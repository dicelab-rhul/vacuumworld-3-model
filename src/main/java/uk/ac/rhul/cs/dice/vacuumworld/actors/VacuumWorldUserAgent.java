package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldUserAgent extends AbstractAgent {
    private static final long serialVersionUID = -2882228263580151389L;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;

    public VacuumWorldUserAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }

    public VacuumWorldUserAgent(VacuumWorldUserAgent toCopy) {
	super(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }

    @Override
    public void sendToActuator(Action<?> action) {
	// TODO Auto-generated method stub
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
    }
    
    @Override
    public void turnLeft() {
	((VacuumWorldActorAppearance) getAppearance()).turnLeft();
    }
    
    @Override
    public void turnRight() {
	((VacuumWorldActorAppearance) getAppearance()).turnRight();
    }
    
    @Override
    public ObjectInputStream getInputChannels() {
	return this.input;
    }

    @Override
    public ObjectOutputStream getOutputChannels() {
	return this.output;
    }

    @Override
    public void setInputChannels(ObjectInputStream input) {
	this.input = input;
    }

    @Override
    public void setOutputChannels(ObjectOutputStream output) {
	this.output = output;
    }
}