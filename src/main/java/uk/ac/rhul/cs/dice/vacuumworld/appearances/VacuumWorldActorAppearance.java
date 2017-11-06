package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.ac.rhul.cs.dice.agent.interfaces.AgentAppearance;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.agents.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.agents.VacuumWorldActuator;
import uk.ac.rhul.cs.dice.vacuumworld.agents.VacuumWorldSensor;

public class VacuumWorldActorAppearance implements AgentAppearance {
    private static final long serialVersionUID = 1958091110354446585L;
    private String id;
    private AgentColor color;
    private Orientation orientation;
    private VacuumWorldMindAppearance mindAppearance;
    private List<VacuumWorldSensor> sensors;
    private List<VacuumWorldActuator> actuators;
    
    public VacuumWorldActorAppearance(String id, AgentColor color, Orientation orientation, VacuumWorldMindAppearance mindappearance, List<VacuumWorldSensor> sensors, List<VacuumWorldActuator> actuators) {
	this.id = id;
	this.color = color;
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
    
    public Orientation getOrientation() {
	return this.orientation;
    }
    
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