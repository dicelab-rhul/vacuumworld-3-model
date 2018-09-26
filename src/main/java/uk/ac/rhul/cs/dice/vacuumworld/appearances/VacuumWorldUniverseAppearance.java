package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agentcontainers.interfaces.UniverseAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorldUniverseAppearance implements UniverseAppearance {
    private static final long serialVersionUID = 1173699153569046653L;
    private VacuumWorldEnvironmentAppearance environment;
    
    public VacuumWorldUniverseAppearance(VacuumWorldEnvironment env) {
	update(env);
    }
    
    public VacuumWorldEnvironmentAppearance getEnvironmentAppearance() {
	return this.environment;
    }
    
    public void update(VacuumWorldEnvironment env) {
	this.environment = env != null ? env.getAppearance() : null;
    }
}