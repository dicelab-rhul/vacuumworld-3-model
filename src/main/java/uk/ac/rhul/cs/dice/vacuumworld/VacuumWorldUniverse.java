package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
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
    
    public VacuumWorldUniverse(String hostname, int port) {
	super(null);
	
	createEnvironment(hostname, port);
	((VacuumWorldUniverseAppearance) getAppearance()).update(getEnvironment());
	
	finalizeUniverse(hostname, port);
    }

    private void finalizeUniverse(String hostname, int port) {
	connectEnvironment();
	connectComponents(hostname, port);
	
	this.stop = getEnvironment().getStopFlag();
    }

    public VacuumWorldUniverse(VacuumWorldEnvironment environment, String hostname, int port) {
	super(new VacuumWorldUniverseAppearance(environment), environment);
	
	finalizeUniverse(hostname, port);
    }

    private void connectComponents(String hostname, int port) {
	getAllActors().forEach(a -> connectToEnvironment(a, hostname, port));
    }
    
    private void connectToEnvironment(VacuumWorldActor actor, String hostname, int port) {
	try {
	    actor.openSocket(hostname, port);
	}
	catch(IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    public VacuumWorldEnvironment getEnvironment() {
	return (VacuumWorldEnvironment) getMainAmbient();
    }
    
    private void createEnvironment(String hostname, int port) {
	addAmbient(new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("easy.json"), this.stop, hostname, port));
    }
    
    private void connectEnvironment() {
	try {
	    Thread t = new Thread(new VacuumWorldEnvironmentBuilderTask(getEnvironment()));	    
	    t.start();

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
    
    public Set<VacuumWorldActor> getAllActors() {
	Set<VacuumWorldActor> actors = new HashSet<>();
	
	((VacuumWorldEnvironment) getMainAmbient()).getGrid().values().stream().filter(VacuumWorldLocation::containsAnActor).map(VacuumWorldLocation::getActorIfAny).forEach(actors::add);
	
	return actors;
    }
    
    public int countActiveBodies() {
	return ((VacuumWorldEnvironmentAppearance) ((VacuumWorldEnvironment) getMainAmbient()).getAppearance()).getAllLocationsWithActiveBodies().size();
    }
}