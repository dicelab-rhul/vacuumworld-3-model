package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAvatar;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.PrincipalListener;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldAvatar extends AbstractAvatar implements VacuumWorldActor {
    private static final long serialVersionUID = 7363668279670343837L;
    private transient Socket socketWithEnvironment;
    private volatile boolean stop;
    private volatile boolean pause;
    private boolean simulatedRun;
    
    public VacuumWorldAvatar(String id, AvatarAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, PrincipalListener principalListener) {
	super(id, appearance, sensors, actuators, principalListener);
    }
    
    public VacuumWorldAvatar(VacuumWorldAvatar toCopy) {
	this(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getPrincipalListener());
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
    public void sendFeedbackToPrincipal(Analyzable... feedback) {
	throw new UnsupportedOperationException(); //TODO maybe change this
    }

    @Override
    public ObjectInputStream getInputChannels() {
	throw new UnsupportedOperationException();
    }

    @Override
    public ObjectOutputStream getOutputChannels() {
	throw new UnsupportedOperationException();
    }

    @Override
    public void setInputChannels(ObjectInputStream input) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void setOutputChannels(ObjectOutputStream output) {
	throw new UnsupportedOperationException();
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
	/*VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getPrincipalListener().decide();
	getPrincipalListener().execute((Action<?>) action);
	sendToActuator((Action<?>) action);
	sendToEnvironment();*/
    }

    private void testRun() {
	while(!this.stop) {
	    System.out.println("Avatar " + getID() + " is being executed.");
	    
	    try {
		Thread.sleep(2000);
	    }
	    catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    }
	}
	
	System.out.println("Avatar " + getID() + ": stop!");
    }

    /*
    private void sendToEnvironment() {
	//TODO
    }
    */

    @Override
    public VacuumWorldActorAppearance getAppearance() {
        return (VacuumWorldActorAppearance) super.getAppearance();
    }
}