package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.cloudstrife9999.logutilities.LogUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAutonomousActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAvatarAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldSerializer {
    private static final String COLOR = "color";
    
    private VacuumWorldSerializer() {}
    
    public static boolean dumpToFile(Path path, JsonObject object) {
	try (JsonWriter writer = new JsonWriter(new FileWriter(new File(path.toUri())))) {
	    writer.setIndent("    ");
	    Streams.write(object, writer);
	    
	    return true;
	}
	catch(IOException e) {
	    LogUtils.log(e);
	    
	    return false;
	}
    }
    
    public static JsonObject serialize(VacuumWorldEnvironment environment) {
	return serialize(environment.getGridReadOnly());
    }
    
    public static JsonObject serialize(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid) {
	JsonObject tree = new JsonObject();
	
	tree.addProperty("size", (int) Math.sqrt(grid.size()));
	tree.add("notable_locations", generateNotableLocations(grid));
	
	return tree;
    }
    
    private static JsonArray generateNotableLocations(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid) {
	JsonArray locations = new JsonArray();
	
	grid.values().stream().filter(VacuumWorldLocation::isNotEmpty).map(VacuumWorldLocation::serialize).forEach(locations::add);
	
	return locations;
    }

    public static JsonObject serialize(VacuumWorldLocationAppearance location) {
	JsonObject locationObject = new JsonObject();
	
	locationObject.addProperty("x", location.getCoordinates().getX());
	locationObject.addProperty("y", location.getCoordinates().getY());
	
	return addBodies(locationObject, location);
    }
    
    private static JsonObject addBodies(JsonObject locationObject, VacuumWorldLocationAppearance location) {
	if(location.isAnActiveBodyThere()) {
	    return addAllBodies(locationObject, location);
	}
	else if(location.isDirtThere()) {
	    locationObject.add("dirt", location.getDirtAppearanceIfAny().serialize());
	}
	else {
	    throw new IllegalArgumentException();
	}
	
	return locationObject;
    }

    private static JsonObject addAllBodies(JsonObject locationObject, VacuumWorldLocationAppearance location) {
	locationObject.add("actor", serializeActor(location));
	
	return addDirtIfAny(locationObject, location);
    }

    private static JsonObject addDirtIfAny(JsonObject locationObject, VacuumWorldLocationAppearance location) {
	if(location.isDirtThere()) {
	    locationObject.add("dirt", location.getDirtAppearanceIfAny().serialize());
	}
	
	return locationObject;
    }

    private static JsonObject serializeActor(VacuumWorldLocationAppearance location) {
	if(location.isACleaningAgentThere()) {
	   return location.getAgentAppearanceIfAny().serialize();
	}
	else if(location.isAUserThere()) {
	    return location.getUserAppearanceIfAny().serialize();
	}
	else if(location.isAnAvatarThere()) {
	    return location.getAvatarAppearanceIfAny().serialize();
	}
	else {
	    throw new IllegalArgumentException();
	}
    }

    public static JsonObject serialize(VacuumWorldDirtAppearance dirt) {
	JsonObject dirtObject = new JsonObject();
	
	dirtObject.addProperty(VacuumWorldSerializer.COLOR, dirt.getColor().toString().toLowerCase());
	
	return dirtObject;
    }
    
    public static JsonObject serialize(VacuumWorldAutonomousActorAppearance actor) {
	JsonObject actorObject = new JsonObject();
	
	actorObject.addProperty("type", actor.isCleaningAgent() ? "cleaning_agent" : "user");
	actorObject.addProperty("id", actor.getId());
	actorObject.addProperty(VacuumWorldSerializer.COLOR, actor.isCleaningAgent() ? actor.getColor().toString().toLowerCase() : null);
	actorObject.addProperty("orientation", actor.getOrientation().toString().toLowerCase());
	actorObject.addProperty("mind", actor.getMindAppearance().getMindName());
	
	JsonObject tempObject = addActorSensorsIfAny(actorObject, actor);
	
	return addActorActuatorsIfAny(tempObject, actor);
    }
    
    private static JsonObject addActorSensorsIfAny(JsonObject actorObject, VacuumWorldAutonomousActorAppearance actor) {
	List<Sensor> sensors = actor.getSensors();
	
	if(!sensors.isEmpty()) {
	    actorObject.add("sensors", getSensorsArray(sensors));
	}
	
	return actorObject;
    }

    private static JsonObject addActorActuatorsIfAny(JsonObject actorObject, VacuumWorldAutonomousActorAppearance actor) {
	List<Actuator> actuators = actor.getActuators();
	
	if(!actuators.isEmpty()) {
	    actorObject.add("actuators", getActuatorsArray(actuators));
	}
	
	return actorObject;
    }

    public static JsonObject serialize(VacuumWorldAvatarAppearance avatar) {
	JsonObject avatarObject = new JsonObject();
	
	avatarObject.addProperty("type", "avatar");
	avatarObject.addProperty("id", avatar.getId());
	avatarObject.add(VacuumWorldSerializer.COLOR, null);
	avatarObject.addProperty("orientation", avatar.getOrientation().toString().toLowerCase());
	avatarObject.addProperty("port", avatar.getListeningPort());
	avatarObject.addProperty("mind", avatar.getMindAppearance().getName());
	
	JsonObject tempObject = addSensorsIfAny(avatarObject, avatar);
	
	return addActuatorsIfAny(tempObject, avatar);
    }

    private static JsonObject addSensorsIfAny(JsonObject avatarObject, VacuumWorldAvatarAppearance avatar) {
	List<Sensor> sensors = avatar.getSensors();
	
	if(!sensors.isEmpty()) {
	    avatarObject.add("sensors", getSensorsArray(sensors));
	}
	
	return avatarObject;
    }

    private static JsonArray getSensorsArray(List<Sensor> sensors) {
	JsonArray sensorsArray = new JsonArray();
	
	sensors.stream().map(VacuumWorldSerializer::serializeSensor).forEach(sensorsArray::add);
	
	return sensorsArray;
    }
    
    private static JsonObject serializeSensor(Sensor sensor) {
	JsonObject sensorObject = new JsonObject();
	
	sensorObject.addProperty("id", sensor.getID());
	sensorObject.addProperty("purpose", sensor.getPurpose().toString().toLowerCase());
	
	return sensorObject;
    }

    private static JsonObject addActuatorsIfAny(JsonObject avatarObject, VacuumWorldAvatarAppearance avatar) {
	List<Actuator> actuators = avatar.getActuators();
	
	if(!actuators.isEmpty()) {
	    avatarObject.add("actuators", getActuatorsArray(actuators));
	}
	
	return avatarObject;
    }

    private static JsonArray getActuatorsArray(List<Actuator> actuators) {
	JsonArray actuatorsArray = new JsonArray();
	
	actuators.stream().map(VacuumWorldSerializer::serializeActuator).forEach(actuatorsArray::add);
	
	return actuatorsArray;
    }
    
    private static JsonObject serializeActuator(Actuator actuator) {
	JsonObject actuatorObject = new JsonObject();
	
	actuatorObject.addProperty("id", actuator.getID());
	actuatorObject.addProperty("purpose", actuator.getPurpose().toString().toLowerCase());
	
	return actuatorObject;
    }
}