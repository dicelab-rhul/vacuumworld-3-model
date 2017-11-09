package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldUniverseAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldUniverse extends AbstractUniverse {
    //private VacuumWorldEnvironment environment;
    private volatile boolean stop;
    
    public VacuumWorldUniverse(VacuumWorldUniverseAppearance appearance) {
	super(appearance);
	
	this.stop = false;
	createEnvironment();
    }

    public VacuumWorldUniverse(VacuumWorldUniverseAppearance appearance, VacuumWorldEnvironment environment) {
	super(appearance, environment);
    }
    
    public VacuumWorldEnvironment getEnvironment() {
	return (VacuumWorldEnvironment) getMainAmbient();
    }
    
    private void createEnvironment() {
	//this.environment = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("easy.json"), false);
	addAmbient(new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("easy.json"), this.stop));
    }
    
    public Set<VacuumWorldCleaningAgent> getAllCleaningAgents() {
	Set<VacuumWorldCleaningAgent> agents = new HashSet<>();
	//this.environment.getGrid().values().stream().filter(VacuumWorldLocation::containsACleaningAgent).map(VacuumWorldLocation::getAgentIfAny).forEach(agents::add);
	((VacuumWorldEnvironment) getMainAmbient()).getGrid().values().stream().filter(VacuumWorldLocation::containsACleaningAgent).map(VacuumWorldLocation::getAgentIfAny).forEach(agents::add);
	
	return agents;
    }
    
    public Set<VacuumWorldUserAgent> getAllUsers() {
	Set<VacuumWorldUserAgent> users = new HashSet<>();
	//this.environment.getGrid().values().stream().filter(VacuumWorldLocation::containsAUser).map(VacuumWorldLocation::getUserIfAny).forEach(users::add);
	((VacuumWorldEnvironment) getMainAmbient()).getGrid().values().stream().filter(VacuumWorldLocation::containsAUser).map(VacuumWorldLocation::getUserIfAny).forEach(users::add);	
	
	return users;
    }
}