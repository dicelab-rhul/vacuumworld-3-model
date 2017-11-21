package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAutonomousActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;

public class VacuumWorldCleaningAgent extends AbstractAgent implements VacuumWorldActor {
    private static final long serialVersionUID = -7231158706838196637L;
    private transient Socket socket;
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private volatile boolean stop;
    private volatile boolean pause;
    private boolean test;

    public VacuumWorldCleaningAgent(String id, VacuumWorldAutonomousActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }

    public VacuumWorldCleaningAgent(VacuumWorldCleaningAgent toCopy) {
	super(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }

    public Socket getSocket() {
	return this.socket;
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
	    LogUtils.log(getID() + " is being executed.");
	    VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getMind().decide();
	    getMind().execute((Action<?>) action);
	    setForMind(sendToEnvironment(action));
	    sendToMind();
	}
    }

    private void testRun() {
	while(!this.stop) {
	    LogUtils.log(getID() + " is being executed.");
	    
	    try {
		Thread.sleep(2000);
	    }
	    catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    }
	}
	
	LogUtils.log(getID() + ": stop!");
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
	    LogUtils.log(getID() + ": waiting for perception.");
	    Perception candidate = (Perception) this.input.readObject();
	    perceptions.add((candidate));
	    
	    while(!(candidate instanceof VacuumWorldPerception)) {
		LogUtils.log(getID() + ": waiting for perception again.");
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
	((VacuumWorldAutonomousActorAppearance) getAppearance()).turnLeft();
    }
    
    @Override
    public void turnRight() {
	((VacuumWorldAutonomousActorAppearance) getAppearance()).turnRight();
    }
    
    @Override
    public VacuumWorldActorAppearance getAppearance() {
        return (VacuumWorldActorAppearance) super.getAppearance();
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
    public void openSocket(String hostname, int port) throws IOException {
	this.socket = new Socket(hostname, port);
	ObjectOutputStream o = new ObjectOutputStream(this.socket.getOutputStream());
	ObjectInputStream i = new ObjectInputStream(this.socket.getInputStream());
	o.writeUTF(getID());
	o.flush();
	setOutputChannels(o);
	setInputChannels(i);
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
}