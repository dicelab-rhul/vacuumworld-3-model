package uk.ac.rhul.cs.dice.vacuumworld.actors;

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
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldCleaningAgent extends AbstractAgent {
    private static final long serialVersionUID = -7231158706838196637L;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private volatile boolean stop;

    public VacuumWorldCleaningAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
	
	this.stop = stop;
    }

    public VacuumWorldCleaningAgent(VacuumWorldCleaningAgent toCopy) {
	super(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }

    public void setStopFlag(boolean stop) {
	this.stop = stop;
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
	/*VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getMind().decide();
	getMind().execute((Action<?>) action);
	sendToActuator((Action<?>) action);
	setForMind(sendToEnvironment());
	sendToMind();*/
	while(!this.stop) {
	    System.out.println("Agent " + getID() + " is being executed.");
	    
	    try {
		Thread.sleep(2000);
	    }
	    catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    }
	}
	
	System.out.println("Agent " + getID() + ": stop!");
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
    
    @Override
    public JsonObject serialize() {
        return ((VacuumWorldActorAppearance) getAppearance()).serialize();
    }
}