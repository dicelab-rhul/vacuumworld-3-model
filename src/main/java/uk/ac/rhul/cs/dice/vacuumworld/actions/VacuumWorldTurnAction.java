package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;

public abstract class VacuumWorldTurnAction extends VacuumWorldAbstractPhysicalAction {
    private static final long serialVersionUID = -2502893283940813507L;
    private Orientation originalOrientation;

    public VacuumWorldTurnAction(VacuumWorldPhysicalActionsEnum type) {
	super(type);
    }
    
    public void setOriginalOrientation(Orientation originalOrientation) {
	this.originalOrientation = originalOrientation;
    }
    
    public Orientation getOriginalOrientation() {
	return this.originalOrientation;
    }
}