package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.interfaces.ActorAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEvent;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldWhitelister;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldPrincipalListener;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldUserMind;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.perception.NothingMoreIncomingPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.StopPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldCheckedException;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

public abstract class VacuumWorldAbstractActor extends AbstractAgent implements VacuumWorldActor {
    private static final long serialVersionUID = 5332798771063837118L;
    private transient Socket socketWithEnvironment;
    private transient ValidatingObjectInputStream input;
    private transient ObjectOutputStream output;
    private volatile boolean stop;
    private volatile boolean pause;
    private boolean simulatedRun;

    public VacuumWorldAbstractActor(String id, ActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }
    
    public VacuumWorldAbstractActor(VacuumWorldAbstractActor toCopy) {
	this(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }
    
    @Override
    public boolean isCleaningAgent() {
	//This holds if and only if there are only cleaning agents, users, and avatars, and they are all mutually exclusive.
	return !isUser() && !isAvatar();
    }
    
    @Override
    public boolean isUser() {
	return VacuumWorldUserMind.class.isAssignableFrom(getMind().getClass());
    }
    
    @Override
    public boolean isAvatar() {
	return VacuumWorldPrincipalListener.class.isAssignableFrom(getMind().getClass());
    }

    @Override
    public Socket getSocketWithEnvironment() {
	return this.socketWithEnvironment;
    }

    @Override
    public void setSocketWithEnvironment(Socket socket) {
	this.socketWithEnvironment = socket;
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
    public boolean isPaused() {
	return this.pause;
    }

    @Override
    public void setRunFlag(boolean simulatedRun) {
	this.simulatedRun = simulatedRun;
    }
    
    @Override
    public void run() {
	try {
	    if (this.simulatedRun) {
		testRun();
	    }
	    else {
		realRun();
	    }
	} 
	catch (Exception e) {
	    LogUtils.fakeLog(e);
	}
    }
    
    private void realRun() {
	getFirstPerceptionIfCleaningAgent();
	cycle();
    }
    
    private void cycle() {
	while (!this.stop) {
	    LogUtils.log(getID() + " is revising...");
	    getMind().revise();

	    LogUtils.log(getID() + " is deciding...");
	    VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getMind().decide();
	    
	    LogUtils.log(getID() + " is executing...");
	    getMind().execute((Action<?>) action);
	    
	    LogUtils.log(getID() + " is waiting for the environment to report back...");
	    setForMind(sendToEnvironment(action));
	    sendToMind();
	}
    }

    private void getFirstPerceptionIfCleaningAgent() {
	if(isCleaningAgent()) {
	    getFirstPerception();
	}
    }
    
    private void getFirstPerception() {
	try {
	    LogUtils.log(getID() + " is perceiving for the first time...");
	    LogUtils.log(getID() + ": waiting for the initial perception from the VacuumWorldEnvironment...");
	    VacuumWorldWhitelister.whitelistPerceptionClasses(this.input);
	    Analyzable firstCyclePerception = (Analyzable) this.input.readObject();
	    getMind().receiveFirstPerception(firstCyclePerception);
	    LogUtils.log(getID() + ": successfully received the initial perception from the VacuumWorldEnvironment...");
	}
	catch(Exception e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }
    
    private void testRun() {
	while (!this.stop) {
	    LogUtils.log(getID() + " is being executed.");
	    final long sleepTime = 2000;
	    
	    try {
		Thread.sleep(sleepTime);
	    }
	    catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    }
	}

	LogUtils.log(getID() + ": stop!");
    }
    
    private Set<Analyzable> sendToEnvironment(VacuumWorldAbstractAction action) {
	try {
	    LogUtils.log(getID() + ": sending event with " + action.getClass().getSimpleName() + " to the environment...");
	    VacuumWorldEvent event = new VacuumWorldEvent(action);
	    this.output.reset();
	    this.output.writeObject(event);
	    this.output.flush();

	    return collectEnviromentFeedback();
	}
	catch (Exception e) {
	    LogUtils.log(e);

	    return Collections.emptySet();
	}
    }
    
    private Set<Analyzable> collectEnviromentFeedback() {
	try {
	    return collectPerceptions();
	}
	catch (IOException e) {
	    manageIOException(e);
	    LogUtils.fakeLog(e);

	    VacuumWorldPerception lastCyclePerception = ((VacuumWorldAbstractMind) getMind()).getPerception();
	    Set<Analyzable> perceptions = new HashSet<>();
	    perceptions.add(lastCyclePerception);
	    
	    return perceptions; 
	}
	catch (Exception e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private void manageIOException(IOException e) {
	if(VacuumWorldCheckedException.class.equals(e.getCause().getClass())) {
	    LogUtils.log(getID() + ": received stop signal from the environment via StopPerception.");
	    this.stop = true;
	}
    }
    
    private Set<Analyzable> collectPerceptions() throws ClassNotFoundException, IOException {
	Set<Analyzable> perceptions = new HashSet<>();
	Perception candidate;

	do {
	    LogUtils.log(getID() + ": waiting for perception.");
	    
	    VacuumWorldWhitelister.whitelistPerceptionClasses(this.input);
	    candidate = (Perception) this.input.readObject();
	    checkStop(candidate);
	    
	    LogUtils.log(getID() + ": got perception: " + candidate.getClass().getSimpleName() + ".");
		
	    perceptions.add(candidate);
	} while (!(candidate instanceof NothingMoreIncomingPerception));
	
	return perceptions;
    }

    private void checkStop(Perception candidate) throws IOException {
	try {
	    if (candidate instanceof StopPerception) {
		throw new VacuumWorldCheckedException(((StopPerception) candidate).getStopMessage());
	    }
	}
	catch(VacuumWorldCheckedException e) {
	    throw new IOException(e);
	}
    }
    
    @Override
    public VacuumWorldActorAppearance getAppearance() {
	return (VacuumWorldActorAppearance) super.getAppearance();
    }

    @Override
    public ValidatingObjectInputStream getInputChannels() {
	return this.input;
    }

    @Override
    public ObjectOutputStream getOutputChannels() {
	return this.output;
    }

    @Override
    public void setInputChannels(ValidatingObjectInputStream input) {
	this.input = input;
    }

    @Override
    public void setOutputChannels(ObjectOutputStream output) {
	this.output = output;
    }
}