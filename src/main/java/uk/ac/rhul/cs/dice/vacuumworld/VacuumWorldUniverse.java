package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldUniverseAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldUniverse extends AbstractUniverse {
    private volatile boolean stop;
    
    public VacuumWorldUniverse() {
	super(null);
	
	createEnvironment();
	((VacuumWorldUniverseAppearance) getAppearance()).update(getEnvironment());
	
	this.stop = getEnvironment().getStopFlag();
    }

    public VacuumWorldUniverse(VacuumWorldEnvironment environment) {
	super(new VacuumWorldUniverseAppearance(environment), environment);
	
	this.stop = getEnvironment().getStopFlag();
    }

    public VacuumWorldEnvironment getEnvironment() {
	return (VacuumWorldEnvironment) getMainAmbient();
    }
    
    private void createEnvironment() {
	addAmbient(new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("easy.json"), this.stop));
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