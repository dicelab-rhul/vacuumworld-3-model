package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;

public abstract class VacuumWorldTurnAction extends VacuumWorldAbstractPhysicalAction {
    private static final long serialVersionUID = -2502893283940813507L;
    private TurnDirection direction;
    private Orientation originalOrientation;

    public VacuumWorldTurnAction(VacuumWorldPhysicalActionsEnum type, TurnDirection direction) {
	super(type);
	
	this.direction = direction;
    }
    
    public void setOriginalOrientation(Orientation originalOrientation) {
	this.originalOrientation = originalOrientation;
    }
    
    public Orientation getOriginalOrientation() {
	return this.originalOrientation;
    }
    
    public TurnDirection getTurnDirection() {
	return this.direction;
    }
    
    public static VacuumWorldTurnAction generate(TurnDirection direction) {
	switch(direction) {
	case LEFT:
	    return new VacuumWorldTurnLeftAction();
	case RIGHT:
	    return new VacuumWorldTurnRightAction();
	default:
	    throw new IllegalArgumentException();
	}
    }
}