package uk.ac.rhul.cs.dice.vacuumworld;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldUniverseAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorldUniverse extends AbstractUniverse {

    public VacuumWorldUniverse(VacuumWorldUniverseAppearance appearance) {
	super(appearance);
    }

    public VacuumWorldUniverse(VacuumWorldUniverseAppearance appearance, VacuumWorldEnvironment environment) {
	super(appearance, environment);
    }
    
    public VacuumWorldEnvironment getEnvironment() {
	return (VacuumWorldEnvironment) getMainAmbient();
    }
}