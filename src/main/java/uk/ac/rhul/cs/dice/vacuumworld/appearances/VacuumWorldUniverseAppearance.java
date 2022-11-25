package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agentcontainers.interfaces.UniverseAppearance;

public class VacuumWorldUniverseAppearance implements UniverseAppearance {
    private static final long serialVersionUID = 1173699153569046653L;
    private boolean initialized;

    public VacuumWorldUniverseAppearance() {
        this.initialized = true;
    }

    public boolean isInitialized() {
        return this.initialized;
    }
}
