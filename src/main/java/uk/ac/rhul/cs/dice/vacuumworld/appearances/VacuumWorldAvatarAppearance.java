package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;

public class VacuumWorldAvatarAppearance implements AvatarAppearance {
    private static final long serialVersionUID = 6754120129442366599L;
    private String id;
    private Orientation orientation;
    private VacuumWorldPrincipalListenerAppearance mindAppearance;
    private List<Sensor> sensors;
    private List<Actuator> actuators;

    public VacuumWorldAvatarAppearance(String id, Orientation orientation, VacuumWorldPrincipalListenerAppearance mindAppearance, List<Sensor> sensors, List<Actuator> actuators) {
	this.id = id;
	this.orientation = orientation;
	this.mindAppearance = mindAppearance;
	this.sensors = ImmutableList.copyOf(sensors);
	this.actuators = ImmutableList.copyOf(actuators);
    }
    
    public String getId() {
	return this.id;
    }
    
    public Orientation getOrientation() {
	return this.orientation;
    }
    
    public VacuumWorldPrincipalListenerAppearance getMindAppearance() {
	return this.mindAppearance;
    }
    
    public List<Actuator> getActuators() {
	return this.actuators;
    }
    
    public List<Sensor> getSensors() {
	return this.sensors;
    }
    
    public void turnLeft() {
	this.orientation = this.orientation.getLeft();
    }
    
    public void turnRight() {
	this.orientation = this.orientation.getRight();
    }
}