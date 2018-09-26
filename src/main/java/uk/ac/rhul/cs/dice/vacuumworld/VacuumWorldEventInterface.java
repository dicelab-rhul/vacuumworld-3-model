package uk.ac.rhul.cs.dice.vacuumworld;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Event;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractActionInterface;

public interface VacuumWorldEventInterface extends Event {
    public VacuumWorldAbstractActionInterface getAction();
}