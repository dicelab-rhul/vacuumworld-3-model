package uk.ac.rhul.cs.dice.vacuumworld.environment;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractCoordinates;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Coordinates;

public class VacuumWorldCoordinates extends AbstractCoordinates {
    private static final long serialVersionUID = 3580947018632549487L;

    public VacuumWorldCoordinates(int x, int y) {
	super(x, y);
    }

    public VacuumWorldCoordinates(Coordinates toCopy) {
	super(toCopy);
    }
}