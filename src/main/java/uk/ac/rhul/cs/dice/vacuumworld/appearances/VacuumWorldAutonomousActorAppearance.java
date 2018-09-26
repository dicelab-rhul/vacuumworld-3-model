package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.interfaces.ActorAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldSerializer;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;

public class VacuumWorldAutonomousActorAppearance implements ActorAppearance, VacuumWorldActorAppearance {
    private static final long serialVersionUID = 1958091110354446585L;
    private String id;
    private AgentColor color; //will be UNDEFINED for users.
    private ActorType type;
    private Orientation orientation;
    private VacuumWorldMindAppearance mindAppearance;
    private List<Sensor> sensors;
    private List<Actuator> actuators;
    
    public VacuumWorldAutonomousActorAppearance(String id, AgentColor color, ActorType type, Orientation orientation, VacuumWorldMindAppearance mindappearance, List<Sensor> sensors, List<Actuator> actuators) {
	this.id = id;
	this.color = color != null ? color : AgentColor.UNDEFINED;
	this.type = type;
	this.orientation = orientation;
	this.mindAppearance = mindappearance;
	this.sensors = ImmutableList.copyOf(sensors);
	this.actuators = ImmutableList.copyOf(actuators);
    }
    
    @Override
    public String getId() {
	return this.id;
    }
    
    @Override
    public AgentColor getColor() {
	return this.color;
    }
    
    @Override
    public ActorType getType() {
	return this.type; //CLEANING_AGENT or USER
    }
    
    @Override
    public Orientation getOrientation() {
	return this.orientation;
    }
    
    @Override
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
    
    @Override
    public VacuumWorldMindAppearance getMindAppearance() {
	return this.mindAppearance;
    }
    
    @Override
    public List<Actuator> getActuators() {
	return this.actuators;
    }
    
    @Override
    public List<Sensor> getSensors() {
	return this.sensors;
    }
    
    @Override
    public JsonObject serialize() {
        return VacuumWorldSerializer.serialize(this);
    }
}