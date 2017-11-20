package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldUniverseAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.buildtasks.VacuumWorldEnvironmentBuilderTask;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;

public class VacuumWorldUniverse extends AbstractUniverse {
    private volatile boolean stop;
    
    public VacuumWorldUniverse() {
	super(null);
	
	createEnvironment();
	((VacuumWorldUniverseAppearance) getAppearance()).update(getEnvironment());
	
	finalizeUniverse();
    }

    private void finalizeUniverse() {
	connectEnvironment();
	connectComponents();
	
	this.stop = getEnvironment().getStopFlag();
    }

    public VacuumWorldUniverse(VacuumWorldEnvironment environment) {
	super(new VacuumWorldUniverseAppearance(environment), environment);
	
	finalizeUniverse();
    }

    private void connectComponents() {
	getAllCleaningAgents().forEach(this::connectToEnvironment);
    }
    
    private void connectToEnvironment(VacuumWorldCleaningAgent agent) {
	try {
	    Socket socket = new Socket("127.0.0.1", 65000);
	    ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
	    ObjectInputStream i = new ObjectInputStream(socket.getInputStream());
	    o.writeUTF(agent.getID());
	    o.flush();
		
	    agent.setOutputChannels(o);
	    agent.setInputChannels(i);
	}
	catch(IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    public VacuumWorldEnvironment getEnvironment() {
	return (VacuumWorldEnvironment) getMainAmbient();
    }
    
    private void createEnvironment() {
	addAmbient(new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("easy.json"), this.stop));
    }
    
    private void connectEnvironment() {
	try {
	    Thread t = new Thread(new VacuumWorldEnvironmentBuilderTask(getEnvironment()));
	    
	    t.start();
	    //t.join();
	    LogUtils.log("Environment socket OK.");
	}
	catch(Exception e) {
	    LogUtils.log(e);
	    Thread.currentThread().interrupt();
	}	
    }
    
    public Set<VacuumWorldCleaningAgent> getAllCleaningAgents() {
	Set<VacuumWorldCleaningAgent> agents = new HashSet<>();
	((VacuumWorldEnvironment) getMainAmbient()).getGrid().values().stream().filter(VacuumWorldLocation::containsACleaningAgent).map(VacuumWorldLocation::getAgentIfAny).forEach(agents::add);
	
	return agents;
    }
    
    public Set<VacuumWorldUserAgent> getAllUsers() {
	Set<VacuumWorldUserAgent> users = new HashSet<>();
	((VacuumWorldEnvironment) getMainAmbient()).getGrid().values().stream().filter(VacuumWorldLocation::containsAUser).map(VacuumWorldLocation::getUserIfAny).forEach(users::add);	
	
	return users;
    }
    
    public Set<VacuumWorldAvatar> getAllAvatars() {
	Set<VacuumWorldAvatar> avatars = new HashSet<>();
	((VacuumWorldEnvironment) getMainAmbient()).getGrid().values().stream().filter(VacuumWorldLocation::containsAnAvatar).map(VacuumWorldLocation::getAvatarIfAny).forEach(avatars::add);	
	
	return avatars;
    }
    
    public int countActiveBodies() {
	return ((VacuumWorldEnvironmentAppearance) ((VacuumWorldEnvironment) getMainAmbient()).getAppearance()).getAllLocationsWithActiveBodies().size();
    }
}