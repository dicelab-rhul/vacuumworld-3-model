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


from argparse import ArgumentParser
from argparse import Namespace

import os
import json


def __fetch_all_minds(minds_folder_path: str) -> list:
    minds: list = []

    for d, _, files in os.walk(minds_folder_path):
        for f in files:
            if f.endswith(".java"):
                minds.append(os.path.join(d, f))
            else:
                print("Skipping %s because it does not end with \".java\".\n" % f)

    return minds


def __cluster_minds(minds: list) -> tuple:
    user_mind: str = ""
    default_mind: str = ""

    um = "VacuumWorldUserMind.java"
    dm = "VacuumWorldDefaultMind.java"

    for mind in minds:
        candidate = mind.split("/")[-1]

        if um in candidate:
            user_mind = __parse(mind_file=mind)
            minds.remove(mind)
        elif dm in candidate:
            default_mind = __parse(mind_file=mind)

    return user_mind, default_mind, __get_concrete_minds(minds=minds)


def __get_concrete_minds(minds: list) -> list:
    concrete: list = []

    for mind in minds:
        try:
            with open(mind, "r") as mind_file:
                lines: list = mind_file.readlines()

                for line in lines:
                    if "public class " in line and " extends " in line and "Mind" in line:
                        concrete.append(__parse(mind_file=mind))
                        break
        except UnicodeDecodeError:
            print("Skipping %s because of a parsing error.\n" % mind)

    return concrete


def __parse(mind_file: str) -> str:
    with open(mind_file, "r") as m:
        lines: list = m.readlines()

        for line in lines:
            if "package " in line:
                package: str = line.replace("\n", "").split(";")[0].split(" ")[-1]

                return package + "." + mind_file.split("/")[-1][:-5]

        return ""


def __regenerate_minds_file(minds_file_path: str, user_mind: str, default_mind: str, all_minds: list) -> None:
    config: dict = {"agents_default": default_mind, "agents": all_minds, "users": user_mind}

    with open(minds_file_path, 'w') as outfile:
        json.dump(config, outfile, indent=4)


def main() -> None:
    parser: ArgumentParser = ArgumentParser(description="Minds updater for VacuumWorld-3.0")
    parser.add_argument("-f", "--minds_folder_path", type=str, required=True, metavar="<minds_folder_path>")
    parser.add_argument("-m", "--minds_file_path", type=str, required=True, metavar="<minds_file_path>")

    args: Namespace = parser.parse_args()
    minds_folder_path = args.minds_folder_path
    minds_file_path = args.minds_file_path

    minds: list = __fetch_all_minds(minds_folder_path=minds_folder_path)

    user_mind, default_mind, all_minds = __cluster_minds(minds=minds)

    __regenerate_minds_file(minds_file_path=minds_file_path, user_mind=user_mind, default_mind=default_mind, all_minds=all_minds)


if __name__ == "__main__":
    main()

END
