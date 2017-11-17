package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentactions.enums.EnvironmentalActionType;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEvent;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;

public class VacuumWorldCleaningAgent extends AbstractAgent {
    private static final long serialVersionUID = -7231158706838196637L;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private volatile boolean stop;
    private volatile boolean pause;
    private boolean test;

    public VacuumWorldCleaningAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }

    public VacuumWorldCleaningAgent(VacuumWorldCleaningAgent toCopy) {
	super(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }

    public void setStopFlag(boolean stop) {
	this.stop = stop;
    }
    
    public void setPauseFlag(boolean pause) {
	this.pause = pause;
    }
    
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
	this.test = false; //TODO remove this
	
	if(this.test) {
	    testRun();
	}
	else {
	    realRun();
	}
    }
    
    private void realRun() {
	while(!this.stop) {
	    System.out.println("Agent " + getID() + " is being executed.");
	    VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getMind().decide();
	    getMind().execute((Action<?>) action);
	    setForMind(sendToEnvironment(action));
	    sendToMind();
	}
    }

    private void testRun() {
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
    
    private Set<Analyzable> sendToEnvironment(VacuumWorldAbstractAction action) {
	try {
	    VacuumWorldEvent event = new VacuumWorldEvent(action);
	    this.output.writeObject(event);
	    this.output.flush();
	    
	    return collectEnviromentFeedback();
	}
	catch(Exception e) {
	    LogUtils.log(e);
	    
	    return Collections.emptySet();
	}
    }

    private Set<Analyzable> collectEnviromentFeedback() {
	try {
	    Set<Analyzable> perceptions = new HashSet<>();
	    Perception candidate = (Perception) this.input.readObject();
	    perceptions.add((candidate));
	    
	    while(!(candidate instanceof VacuumWorldPerception)) {
		candidate = (Perception) this.input.readObject();
		perceptions.add((candidate));
	    }
	    
	    return perceptions;
	}
	catch(Exception e) {
	    throw new VacuumWorldRuntimeException(e);
	}
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