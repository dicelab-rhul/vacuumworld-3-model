package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.cloudstrife9999.logutilities.LogUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgentMind;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.enums.SensorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActuator;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldSensor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldPrincipalListener;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAutonomousActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAvatarAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldMindAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldPrincipalListenerAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VWJSON;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

public class VacuumWorldParser {

    private VacuumWorldParser() {}

    public static Map<VacuumWorldCoordinates, VacuumWorldLocation> parseConfiguration(String path) {
        try (FileReader fr = new FileReader(path)) {
            Gson parser = new Gson();
            JsonObject tree = parser.fromJson(new BufferedReader(fr), JsonObject.class);

            return buildMap(tree);
        }
        catch (IOException e) {
            LogUtils.log(e);

            return Collections.emptyMap();
        }
    }

    public static Map<VacuumWorldCoordinates, VacuumWorldLocation> parseConfiguration(JsonObject json) {
        return buildMap(json);
    }

    private static Map<VacuumWorldCoordinates, VacuumWorldLocation> buildMap(JsonObject tree) {
        JsonElement sizeElement = tree.get(VWJSON.SIZE);
        int size = sizeElement.getAsInt();

        if (size < VacuumWorldEnvironment.MINIMUM_SIZE || size > VacuumWorldEnvironment.MAXIMUM_SIZE) {
            throw new VacuumWorldRuntimeException("Size out of bounds: accepted [" + VacuumWorldEnvironment.MINIMUM_SIZE + ", " + VacuumWorldEnvironment.MAXIMUM_SIZE + "], got " + size + ".");
        }

        return buildLocations(size, tree.get(VWJSON.NOTABLE_LOCATIONS));
    }

    private static Map<VacuumWorldCoordinates, VacuumWorldLocation> buildLocations(int size, JsonElement notableLocations) {
        Map<VacuumWorldCoordinates, VacuumWorldLocation> map = new ConcurrentHashMap<>();
        JsonArray locations = notableLocations.getAsJsonArray();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                VacuumWorldCoordinates coordinates = new VacuumWorldCoordinates(i, j);
                map.put(coordinates, parseLocation(coordinates, locations, size));
            }
        }

        return map;
    }

    private static VacuumWorldLocation parseLocation(VacuumWorldCoordinates coordinates, JsonArray locations, int size) {
        for (JsonElement location : locations) {
            JsonObject locationRepresentation = location.getAsJsonObject();

            if (locationRepresentation.get(VWJSON.X).getAsInt() == coordinates.getX() && locationRepresentation.get(VWJSON.Y).getAsInt() == coordinates.getY()) {
                return parseLocation(coordinates, locationRepresentation, size);
            }
        }

        return new VacuumWorldLocation(coordinates, VacuumWorldEnvironment.getWallInfo(coordinates, size));
    }

    private static VacuumWorldLocation parseLocation(VacuumWorldCoordinates coordinates, JsonObject locationRepresentation, int size) {
        VacuumWorldLocation tmp = new VacuumWorldLocation(coordinates, VacuumWorldEnvironment.getWallInfo(coordinates, size));
        VacuumWorldLocation temp = addActorIfPresent(tmp, locationRepresentation);

        return addDirtIfPresent(temp, locationRepresentation);
    }

    private static VacuumWorldLocation addActorIfPresent(VacuumWorldLocation location, JsonObject locationRepresentation) {
        VacuumWorldLocation toReturn = location;
        JsonElement actorRepresentation = locationRepresentation.get(VWJSON.ACTOR);

        if (actorRepresentation != null && !actorRepresentation.isJsonNull()) {
            toReturn.addActor(buildActor(actorRepresentation.getAsJsonObject()));
        }

        return toReturn;
    }

    private static VacuumWorldLocation addDirtIfPresent(VacuumWorldLocation location, JsonObject locationRepresentation) {
        VacuumWorldLocation toReturn = location;
        JsonElement dirtRepresentation = locationRepresentation.get(VWJSON.DIRT);

        if (dirtRepresentation != null && !dirtRepresentation.isJsonNull()) {
            toReturn.addDirt(buildDirt(dirtRepresentation.getAsJsonObject()));
        }

        return toReturn;
    }

    private static VacuumWorldActor buildActor(JsonObject actorRepresentation) {
        String id = actorRepresentation.get(VWJSON.ACTOR_ID).getAsString();
        JsonElement colorRepresentation = actorRepresentation.get(VWJSON.ACTOR_COLOR);
        AgentColor color = colorRepresentation.isJsonNull() ? null : AgentColor.valueOf(colorRepresentation.getAsString().toUpperCase());
        Orientation orientation = Orientation.valueOf(actorRepresentation.get(VWJSON.ORIENTATION).getAsString().toUpperCase());
        List<Sensor> sensors = buildSensors(actorRepresentation.get(VWJSON.SENSORS).getAsJsonArray());
        List<Actuator> actuators = buildActuators(actorRepresentation.get(VWJSON.ACTUATORS).getAsJsonArray());
        ActorType actorType = ActorType.valueOf(actorRepresentation.get(VWJSON.TYPE).getAsString().toUpperCase());

        return buildActorSwitcher(actorRepresentation, id, color, orientation, sensors, actuators, actorType);
    }

    private static VacuumWorldActor buildActorSwitcher(JsonObject actorRepresentation, String id, AgentColor color, Orientation orientation, List<Sensor> sensors, List<Actuator> actuators, ActorType actorType) {
        switch (actorType) {
            case CLEANING_AGENT:
            case USER:
                return buildAutonomousActor(actorRepresentation, id, color, orientation, sensors, actuators, actorType);
            case AVATAR:
                return buildAvatar(actorRepresentation, id, orientation, sensors, actuators);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static VacuumWorldActor buildAutonomousActor(JsonObject actorRepresentation, String id, AgentColor color, Orientation orientation, List<Sensor> sensors, List<Actuator> actuators, ActorType actorType) {
        try {
            AbstractAgentMind mind = (AbstractAgentMind) Class.forName(actorRepresentation.get(VWJSON.MIND).getAsString()).getConstructor(String.class).newInstance(id);
            VacuumWorldMindAppearance mindAppearance = new VacuumWorldMindAppearance(actorRepresentation.get(VWJSON.MIND).getAsString());
            VacuumWorldAutonomousActorAppearance appearance = new VacuumWorldAutonomousActorAppearance(id, color, actorType, orientation, mindAppearance, sensors, actuators);

            return buildAutonomousActor(id, appearance, sensors, actuators, mind);
        }
        catch (Exception e) {
            throw new VacuumWorldRuntimeException(e);
        }
    }

    private static VacuumWorldActor buildAvatar(JsonObject actorRepresentation, String candidateId, Orientation orientation, List<Sensor> sensors, List<Actuator> actuators) {
        try {
            String id = sanitize(candidateId);
            int port = actorRepresentation.get(VWJSON.AVATAR_PORT).getAsInt();
            VacuumWorldPrincipalListener listener = new VacuumWorldPrincipalListener(new ServerSocket(port));
            VacuumWorldPrincipalListenerAppearance mindAppearance = new VacuumWorldPrincipalListenerAppearance(listener.getName(), port);
            AvatarAppearance avatarAppearance = new VacuumWorldAvatarAppearance(id, orientation, port, mindAppearance, sensors, actuators);

            return new VacuumWorldAvatar(id, avatarAppearance, sensors, actuators, listener);
        }
        catch (Exception e) {
            throw new VacuumWorldRuntimeException(e);
        }
    }

    private static VacuumWorldActor buildAutonomousActor(String candidateId, VacuumWorldAutonomousActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AbstractAgentMind mind) {
        String id = sanitize(candidateId);

        switch (appearance.getType()) {
            case CLEANING_AGENT:
                return new VacuumWorldCleaningAgent(id, appearance, sensors, actuators, mind);
            case USER:
                return new VacuumWorldUserAgent(id, appearance, sensors, actuators, mind);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String sanitize(String candidateId) {
        if (!VacuumWorldComponentsManager.getIds().contains(candidateId)) {
            VacuumWorldComponentsManager.addId(candidateId);

            return candidateId;
        }
        else {
            String newId = "Actor-" + UUID.randomUUID().toString();

            LogUtils.log("Overriding the ID " + candidateId + " with " + newId + " to avoid conflicts");

            VacuumWorldComponentsManager.addId(newId);

            return newId;
        }
    }

    private static List<Actuator> buildActuators(JsonArray actuatorsList) {
        List<Actuator> actuators = new ArrayList<>();
        actuatorsList.forEach(a -> actuators.add(parseActuator(a.getAsJsonObject())));

        return actuators;
    }

    private static Actuator parseActuator(JsonObject actuatorRepresentation) {
        ActuatorPurposeEnum purpose = ActuatorPurposeEnum.valueOf(actuatorRepresentation.get(VWJSON.SENSOR_ACTUATOR_PURPOSE).getAsString().toUpperCase());

        return new VacuumWorldActuator(purpose);
    }

    private static List<Sensor> buildSensors(JsonArray sensorsList) {
        List<Sensor> sensors = new ArrayList<>();
        sensorsList.forEach(s -> sensors.add(parseSensor(s.getAsJsonObject())));

        return sensors;
    }

    private static Sensor parseSensor(JsonObject sensorRepresentation) {
        SensorPurposeEnum purpose = SensorPurposeEnum.valueOf(sensorRepresentation.get(VWJSON.SENSOR_ACTUATOR_PURPOSE).getAsString().toUpperCase());

        return new VacuumWorldSensor(purpose);
    }

    private static VacuumWorldDirt buildDirt(JsonObject dirtRepresentation) {
        String color = dirtRepresentation.get(VWJSON.DIRT_COLOR).getAsString();

        return new VacuumWorldDirt(VacuumWorldDirtColor.valueOf(color.toUpperCase()));
    }
}
