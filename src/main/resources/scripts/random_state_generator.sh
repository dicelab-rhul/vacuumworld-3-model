#!/bin/bash

DEFAULT_PYTHON3=$(/usr/bin/env python3 --version |& cut -d' ' -f2)
MAJOR=$(echo ${DEFAULT_PYTHON3} | cut -d'.' -f1)
MINOR=$(echo ${DEFAULT_PYTHON3} | cut -d'.' -f2)

if [[ ${MAJOR} < "3" ]]; then
    echo "Python 3.6+ is required. Aborting..."
    
    exit 1
fi

if [[ ${MINOR} -lt "6" ]]; then
    echo "Python 3.6+ is required. Aborting..."
    
    exit 1
fi

/usr/bin/env python3 - <<END

import random
import uuid
import json
import time
import sys

from argparse import ArgumentParser
from argparse import Namespace


def __add_user(user: bool) -> int:
    if user:
        return 1
    else:
        return 0


def __check_for_consistency(size: int, number_of_active_bodies: int, number_of_dirts: int, user: bool) -> None:
    if size < 1:
        raise Exception("The grid size cannot be < 1.")

    if number_of_active_bodies > size * size:
        raise Exception("The number of active bodies cannot be greater than the number of available locations.")

    if number_of_dirts > size * size:
        raise Exception("The number of dirts cannot be greater than the number of available locations.")

    if number_of_active_bodies < 1:
        raise Exception("The number of cleaning agents must be at least 1.")

    if number_of_active_bodies == 1 and user:
        raise Exception("The number of cleaning agents must be at least 1. The user does not count, as it is not a cleaning agent.")        


def __generate_state(size: int=5, nga: int=1, noa: int=0, nwa: int=0, user: bool=False, ngd: int=1, nod: int=0, wm: str="", om: str="", gm: str="") -> dict:
    number_of_active_bodies = nga + noa + nwa + __add_user(user=user)
    number_of_dirts = ngd + nod

    __check_for_consistency(size=size, number_of_active_bodies=number_of_active_bodies, number_of_dirts=number_of_dirts, user=user)

    return {"size": size, "notable_locations": __create_notable_locations(size=size, nga=nga, noa=noa, nwa=nwa, user=user, ngd=ngd, nod=nod, gm=gm, om=om, wm=wm)}


def __create_notable_locations(size: int, nga: int, noa: int, nwa: int, user: bool, ngd: int, nod: int, gm: str, om: str, wm: str) -> list:
    active_bodies: list = __generate_active_bodies(nga=nga, noa=noa, nwa=nwa, user=user, gm=gm, om=om, wm=wm)
    dirts: list = __generate_dirts(ngd=ngd, nod=nod)
    coordinates_for_bodies: list = [(i, j) for i in range(size) for j in range(size)]
    coordinates_for_dirts: list = [(i, j) for i in range(size) for j in range(size)]

    grid: dict = {}

    for body in active_bodies:
        random.shuffle(coordinates_for_bodies)
        coordinates = coordinates_for_bodies.pop()
        grid[coordinates] = [body]

    for dirt in dirts:
        random.shuffle(coordinates_for_dirts)
        coordinates = coordinates_for_dirts.pop()

        if coordinates in grid.keys():
            grid[coordinates].append(dirt)
        else:
            grid[coordinates] = [dirt]

    return __create_locations_from_grid(grid=grid)


def __generate_active_bodies(nga: int, noa: int, nwa: int, user: bool, gm: str, om: str, wm: str) -> list:
    actors: list = []

    for i in range(nga):
        actors.append(__generate_actor(actor_type="cleaning_agent", color="green", mind=gm))

    for i in range(noa):
        actors.append(__generate_actor(actor_type="cleaning_agent", color="orange", mind=om))

    for i in range(nwa):
        actors.append(__generate_actor(actor_type="cleaning_agent", color="white", mind=wm))

    if user:
        actors.append(__generate_actor(actor_type="user", color=None, mind="uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldUserMind"))

    return actors


def __generate_actor(actor_type: str, color, mind: str) -> dict:
    orientations = ["north", "south", "west", "east"]
    random.shuffle(orientations)

    actor: dict = {"type": actor_type, "color": color, "orientation": orientations[0], "mind": mind}

    if actor_type == "cleaning_agent":
        actor["id"] = "Agent-" + str(uuid.uuid4())
    else:
        actor["id"] = "User-" + str(uuid.uuid4())

    actor["sensors"] = __generate_appendices(appendix_type="sensors")
    actor["actuators"] = __generate_appendices(appendix_type="actuators")

    return actor


def __generate_appendices(appendix_type: str) -> list:
    appendices = []

    if appendix_type == "sensors":
        appendices.append({"id": "Sensor-1", "purpose": "see"})
        appendices.append({"id": "Sensor-2", "purpose": "listen"})
        appendices.append({"id": "Sensor-3", "purpose": "other"})
    else:
        appendices.append({"id": "Actuator-1", "purpose": "act_physically"})
        appendices.append({"id": "Actuator-2", "purpose": "speak"})
        appendices.append({"id": "Actuator-3", "purpose": "other"})

    return appendices


def __generate_dirts(ngd: int, nod: int) -> list:
    dirts: list = []

    for i in range(ngd):
        dirts.append({"color": "green"})

    for i in range(nod):
        dirts.append({"color": "orange"})

    return dirts


def __create_locations_from_grid(grid: dict) -> list:
    notable_locations: list = []

    for key, value in grid.items():
        x: int = key[0]
        y: int = key[1]

        actor, dirt = __get_actor_and_dirt_if_any(value=value)

        notable_locations.append(__create_location(x=x, y=y, actor=actor, dirt=dirt))

    return notable_locations


def __create_location(x: int, y: int, actor: dict, dirt: dict) -> dict:
    location: dict = {"x": x, "y": y}

    if actor is not None:
        location["actor"] = actor

    if dirt is not None:
        location["dirt"] = dirt

    return location


def __get_actor_and_dirt_if_any(value: dict) -> tuple:
    if len(value) == 2:
        return value[0], value[1]

    candidate: dict = value[0]

    if "type" in candidate.keys():
        if candidate["type"] in ["cleaning_agent", "user"]:
            return candidate, None
        else:
            raise Exception("Malformed actor (no type).")
    else:
        return None, candidate


def __dump_state(state: dict) -> None:
    file_path: str = str(time.time()) + ".json"

    with open(file_path, 'w') as outfile:
        json.dump(state, outfile, indent=4)


def main() -> None:
    parser: ArgumentParser = ArgumentParser(description="Random state generator for VacuumWorld-3.0")
    parser.add_argument("-s", "--grid-size", type=int, required=False, metavar="<grid_size>", default=5)
    parser.add_argument("-nga", "--number-of-green-agents", type=int, required=False, metavar="<number-of-green-agents>", default=1)
    parser.add_argument("-noa", "--number-of-orange-agents", type=int, required=False, metavar="<number-of-orange-agents>", default=0)
    parser.add_argument("-nwa", "--number-of-white-agents", type=int, required=False, metavar="<number-of-white-agents>", default=0)
    parser.add_argument("-u", "--user-present", type=bool, required=False, metavar="<user-present>", default=False)
    parser.add_argument("-ngd", "--number-of-green-dirts", type=int, required=False, metavar="<number-of-green-dirts>", default=1)
    parser.add_argument("-nod", "--number-of-orange-dirts", type=int, required=False, metavar="<number-of-orange-dirts>", default=0)
    parser.add_argument("-gm", "--green-mind", type=str, required=False, metavar="<green-mind>", default="uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldGreenMind")
    parser.add_argument("-om", "--orange-mind", type=str, required=False, metavar="<orange-mind>", default="uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldOrangeMind")
    parser.add_argument("-wm", "--white-mind", type=str, required=False, metavar="<white-mind>", default="uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldWhiteMind")

    args: Namespace = parser.parse_args()

    print("Welcome to the VacuumWorld-3.0 random states generator!")
    print("A state will be now generated.")
    print("If you want at least a partial control over the randomization, run %s -h" % sys.argv[0])

    size: int = args.grid_size
    nga: int = args.number_of_green_agents
    noa: int = args.number_of_orange_agents
    nwa: int = args.number_of_white_agents
    user: bool = args.user_present
    ngd: int = args.number_of_green_dirts
    nod: int = args.number_of_orange_dirts
    gm: str = args.green_mind
    om: str = args.orange_mind
    wm: str = args.white_mind
    state: dict = __generate_state(size=size, nga=nga, noa=noa, nwa=nwa, user=user, ngd=ngd, nod=nod, gm=gm, om=om, wm=wm)

    __dump_state(state)


if __name__ == "__main__":
    main()

END
