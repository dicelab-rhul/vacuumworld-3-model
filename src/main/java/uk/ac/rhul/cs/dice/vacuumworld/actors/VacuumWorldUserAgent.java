package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentactions.enums.EnvironmentalActionType;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAutonomousActorAppearance;

public class VacuumWorldUserAgent extends AbstractAgent implements VacuumWorldActor {
    private static final long serialVersionUID = -2882228263580151389L;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private volatile boolean stop;
    private volatile boolean pause;
    private boolean test;

    public VacuumWorldUserAgent(String id, VacuumWorldAutonomousActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }

    public VacuumWorldUserAgent(VacuumWorldUserAgent toCopy) {
	super(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }
    
    @Override
    public void setStopFlag(boolean stop) {
	this.stop = stop;
    }
    
    @Override
    public void setPauseFlag(boolean pause) {
	this.pause = pause;
    }
    
    @Override
    public void toggleTest() {
	this.test = true;
    }

    @Override
    public void sendToActuator(Action<?> action) {
	EnvironmentalActionType type = (EnvironmentalActionType) action.getGenericType();
	((VacuumWorldActuator) getActuatorFromActionType(type)).validateExecution(action);
    }
    
    private Actuator getActuatorFromActionType(EnvironmentalActionType type) {
	switch (type) {
	case PHYSICAL:
	    return getSpecificActuators(ActuatorPurposeEnum.ACT_PHYSICALLY).get(0);
	case COMMUNICATIVE:
	    return getSpecificActuators(ActuatorPurposeEnum.SPEAK).get(0);
	case SENSING:
	    return getSpecificActuators(ActuatorPurposeEnum.OTHER).get(0);
	default:
	    throw new UnsupportedOperationException("No compatible actuator found.");
	}
    }

    @Override
    public void run() {
	if(this.test) {
	    testRun();
	}
	else {
	    realRun();
	}
    }
    
    private void realRun() {
	/*while(!this.stop) {
	    VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getMind().decide();
	    getMind().execute((Action<?>) action);
	    sendToActuator((Action<?>) action);
	    setForMind(sendToEnvironment());
	    sendToMind();
	}*/
    }

    private void testRun() {
	while(!this.stop) {
	    System.out.println("User " + getID() + " is being executed.");
	    
	    try {
		Thread.sleep(2000);
	    }
	    catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    }
	}
	
	System.out.println("User " + getID() + ": stop!");
    }

    private Set<Analyzable> sendToEnvironment() {
	/* TODO
	 * create an event
	 * send it to the output channel
	 * wait for all the perceptions
	 * collect the perceptions into a set
	 * return the set.
	 */
	
	//TODO change this
	return Collections.emptySet();
    }
    
    @Override
    public VacuumWorldActorAppearance getAppearance() {
        return (VacuumWorldActorAppearance) super.getAppearance();
    }
    
    @Override
    public void turnLeft() {
	((VacuumWorldAutonomousActorAppearance) getAppearance()).turnLeft();
    }
    
    @Override
    public void turnRight() {
	((VacuumWorldAutonomousActorAppearance) getAppearance()).turnRight();
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
    
    @Override
    public JsonObject serialize() {
        return getAppearance().serialize();
    }

    @Override
    public void openSocket(String hostname, int port) throws IOException {
	// TODO Auto-generated method stub
	
    }
}