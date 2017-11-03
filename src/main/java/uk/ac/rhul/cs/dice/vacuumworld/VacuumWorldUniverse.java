package uk.ac.rhul.cs.dice.vacuumworld;

import uk.ac.rhul.cs.dice.agentcontainers.interfaces.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldUniverseAppearance;

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