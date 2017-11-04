package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;

public abstract class VacuumWorldTurnAction extends VacuumWorldAbstractPhysicalAction {

    public VacuumWorldTurnAction(VacuumWorldPhysicalActionsEnum type) {
	super(type);
    }
}