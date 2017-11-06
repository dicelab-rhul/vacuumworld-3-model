package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgentMind;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.enums.SensorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActuator;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldSensor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldMindAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;

public class VacuumWorldParser {

    private VacuumWorldParser() {}
    
    public static Map<VacuumWorldCoordinates, VacuumWorldLocation> parseConfiguration(String path) {
	try(FileReader fr = new FileReader(path)) {
	    Gson parser = new Gson();
	    JsonObject tree = parser.fromJson(new BufferedReader(fr), JsonObject.class);
	    
	    return buildMap(tree);
	}
	catch(IOException e) {
	    return Collections.emptyMap();
	}
    }

    private static Map<VacuumWorldCoordinates, VacuumWorldLocation> buildMap(JsonObject tree) {
	JsonElement sizeElement = tree.get("size");
	int size = sizeElement.getAsInt();
	
	if(size < VacuumWorldEnvironment.MINIMUM_SIZE || size > VacuumWorldEnvironment.MAXIMUM_SIZE) {
	    throw new VacuumWorldRuntimeException("Size out of bounds: accepted [" + VacuumWorldEnvironment.MINIMUM_SIZE + ", " + VacuumWorldEnvironment.MAXIMUM_SIZE + "], got " + size + ".");
	}
	
	return buildLocations(size, tree.get("notable_locations"));
    }

    private static Map<VacuumWorldCoordinates, VacuumWorldLocation> buildLocations(int size, JsonElement notableLocations) {
	Map<VacuumWorldCoordinates, VacuumWorldLocation> map = new ConcurrentHashMap<>();
	JsonArray locations = notableLocations.getAsJsonArray();
	
	for(int i = 0; i < size; i++) {
	    for(int j = 0; j < size; j++) {
		VacuumWorldCoordinates coordinates = new VacuumWorldCoordinates(i, j);
		map.put(coordinates, parseLocation(coordinates, locations, size));
	    }
	}
	
	return map;
    }

    private static VacuumWorldLocation parseLocation(VacuumWorldCoordinates coordinates, JsonArray locations, int size) {
	for(JsonElement location : locations) {
	    JsonObject locationRepresentation = location.getAsJsonObject();
	    
	    if(locationRepresentation.get("x").getAsInt() == coordinates.getX() && locationRepresentation.get("y").getAsInt() == coordinates.getY()) {
		return parseLocation(coordinates, locationRepresentation, size);
	    }
	}
	
	return new VacuumWorldLocation(coordinates, VacuumWorldEnvironment.getWallInfo(coordinates, size));
    }
    
    private static VacuumWorldLocation parseLocation(VacuumWorldCoordinates coordinates, JsonObject locationRepresentation, int size) {
	VacuumWorldLocation location = new VacuumWorldLocation(coordinates, VacuumWorldEnvironment.getWallInfo(coordinates, size));
	location = addActorIfPresent(location, locationRepresentation);
	location = addDirtIfPresent(location, locationRepresentation);
	
	return location;
    }

    private static VacuumWorldLocation addActorIfPresent(VacuumWorldLocation location, JsonObject locationRepresentation) {
	VacuumWorldLocation toReturn = location;
	JsonElement actorRepresentation = locationRepresentation.get("actor");
	
	if(actorRepresentation != null && !actorRepresentation.isJsonNull()) {
	    toReturn.addActor(buildActor(actorRepresentation.getAsJsonObject()));
	}
	
	return toReturn;
    }

    private static VacuumWorldLocation addDirtIfPresent(VacuumWorldLocation location, JsonObject locationRepresentation) {
	VacuumWorldLocation toReturn = location;
	JsonElement dirtRepresentation = locationRepresentation.get("dirt");
	
	if(dirtRepresentation != null && !dirtRepresentation.isJsonNull()) {
	    toReturn.addDirt(buildDirt(dirtRepresentation.getAsJsonObject()));
	}
	
	return toReturn;
    }
    
    private static Actor buildActor(JsonObject actorRepresentation) {
	try {
	    String id = actorRepresentation.get("id").getAsString();
	    JsonElement colorRepresentation = actorRepresentation.get("color");
	    AgentColor color = colorRepresentation.isJsonNull() ? null : AgentColor.valueOf(colorRepresentation.getAsString().toUpperCase());
	    Orientation orientation = Orientation.valueOf(actorRepresentation.get("orientation").getAsString().toUpperCase());
	    AbstractAgentMind mind = (AbstractAgentMind) Class.forName(actorRepresentation.get("mind").getAsString()).newInstance();
	    VacuumWorldMindAppearance mindAppearance = new VacuumWorldMindAppearance(actorRepresentation.get("mind").getAsString());
	    List<Sensor> sensors = buildSensors(actorRepresentation.get("sensors").getAsJsonArray());
	    List<Actuator> actuators = buildActuators(actorRepresentation.get("actuators").getAsJsonArray());
	    ActorType actorType = ActorType.valueOf(actorRepresentation.get("type").getAsString().toUpperCase());
	    VacuumWorldActorAppearance appearance = new VacuumWorldActorAppearance(id, color, actorType, orientation, mindAppearance, sensors, actuators);
	
	    return buildActor(id, appearance, sensors, actuators, mind);
	}
	catch(Exception e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private static Actor buildActor(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AbstractAgentMind mind) {
	switch(appearance.getType()) {
	case CLEANING_AGENT:
	    return new VacuumWorldCleaningAgent(id, appearance, sensors, actuators, mind);
	case USER:
	    return new VacuumWorldUserAgent(id, appearance, sensors, actuators, mind);
	case AVATAR:
	    return buildAvatar(id, appearance, sensors, actuators, mind);
	default:
	    throw new IllegalArgumentException();
	}
    }

    private static Actor buildAvatar(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AbstractAgentMind mind) {
	// TODO Auto-generated method stub
	return null;
    }

    private static List<Actuator> buildActuators(JsonArray actuatorsList) {
	List<Actuator> actuators = new ArrayList<>();
	actuatorsList.forEach(a -> actuators.add(parseActuator(a.getAsJsonObject())));
	
	return actuators;
    }

    private static Actuator parseActuator(JsonObject actuatorRepresentation) {
	ActuatorPurposeEnum purpose = ActuatorPurposeEnum.valueOf(actuatorRepresentation.get("purpose").getAsString());
	
	return new VacuumWorldActuator(purpose);
    }

    private static List<Sensor> buildSensors(JsonArray sensorsList) {
	List<Sensor> sensors = new ArrayList<>();
	sensorsList.forEach(s -> sensors.add(parseSensor(s.getAsJsonObject())));
	
	return sensors;
    }

    private static Sensor parseSensor(JsonObject sensorRepresentation) {
	SensorPurposeEnum purpose = SensorPurposeEnum.valueOf(sensorRepresentation.get("purpose").getAsString());
	
	return new VacuumWorldSensor(purpose);
    }

    private static VacuumWorldDirt buildDirt(JsonObject dirtRepresentation) {
	String color = dirtRepresentation.get("color").getAsString();
	
	return new VacuumWorldDirt(VacuumWorldDirtColor.valueOf(color.toUpperCase()));
    }
}