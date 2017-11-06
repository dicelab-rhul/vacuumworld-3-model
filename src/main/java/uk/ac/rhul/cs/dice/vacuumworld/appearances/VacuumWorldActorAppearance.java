package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.ac.rhul.cs.dice.agent.interfaces.ActorAppearance;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActuator;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldSensor;

public class VacuumWorldActorAppearance implements ActorAppearance {
    private static final long serialVersionUID = 1958091110354446585L;
    private String id;
    private AgentColor color; //will be UNDEFINED for avatars and users.
    private ActorType type;
    private Orientation orientation;
    private VacuumWorldMindAppearance mindAppearance;
    private List<VacuumWorldSensor> sensors;
    private List<VacuumWorldActuator> actuators;
    
    public VacuumWorldActorAppearance(String id, AgentColor color, ActorType type, Orientation orientation, VacuumWorldMindAppearance mindappearance, List<VacuumWorldSensor> sensors, List<VacuumWorldActuator> actuators) {
	this.id = id;
	this.color = color;
	this.type = type;
	this.orientation = orientation;
	this.mindAppearance = mindappearance;
	this.sensors = ImmutableList.copyOf(sensors);
	this.actuators = ImmutableList.copyOf(actuators);
    }
    
    public String getId() {
	return this.id;
    }
    
    public AgentColor getColor() {
	return this.color;
    }
    
    public boolean isGreenAgent() {
	return AgentColor.GREEN.equals(this.color);
    }
    
    public boolean isOrangeAgent() {
	return AgentColor.ORANGE.equals(this.color);
    }
    
    public boolean isWhiteAgent() {
	return AgentColor.WHITE.equals(this.color);
    }
    
    public ActorType getType() {
	return this.type;
    }
    
    public boolean isCleaningAgent() {
	return ActorType.CLEANING_AGENT.equals(this.type);
    }
    
    public boolean isUser() {
	return ActorType.USER.equals(this.type);
    }
    
    public boolean isAvatar() {
	return ActorType.AVATAR.equals(this.type);
    }
    
    public Orientation getOrientation() {
	return this.orientation;
    }
    
    @Override
    public VacuumWorldMindAppearance getMindAppearance() {
	return this.mindAppearance;
    }
    
    public List<VacuumWorldActuator> getActuators() {
	return this.actuators;
    }
    
    public List<VacuumWorldSensor> getSensors() {
	return this.sensors;
    }
    
    public void turnLeft() {
	this.orientation = this.orientation.getLeft();
    }
    
    public void turnRight() {
	this.orientation = this.orientation.getRight();
    }
}