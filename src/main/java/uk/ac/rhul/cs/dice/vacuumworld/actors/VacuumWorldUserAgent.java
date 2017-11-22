package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldUserAgent extends AbstractAgent implements VacuumWorldActor {
    private static final long serialVersionUID = -2882228263580151389L;
    private transient Socket socketWithEnvironment; //TODO initialize this
    private transient ObjectInputStream input;
    private transient ObjectOutputStream output;
    private volatile boolean stop;
    private volatile boolean pause;
    private boolean simulatedRun;

    public VacuumWorldUserAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }

    public VacuumWorldUserAgent(VacuumWorldUserAgent toCopy) {
	this(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
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
    public void setRunFlag(boolean simulatedRun) {
	this.simulatedRun = simulatedRun;
    }

    @Override
    public void run() {
	if(this.simulatedRun) {
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