package uk.ac.rhul.cs.dice.vacuumworld.actions.advanced;

import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;

public class GoToPositionGoal implements VacuumWorldSimplePhysicalGoal<PhysicalAction<VacuumWorldPhysicalActionsEnum>> {
    private VacuumWorldCoordinates target;
    private GoToPositionPlan plan;
    
    public GoToPositionGoal(VacuumWorldCoordinates target) {
	this.target = target;
	this.plan = new GoToPositionPlan();
    }

    public void buildPlan(VacuumWorldCoordinates current, Orientation orientation) {
	int horizontalDifference = this.target.getHorizontalDifference(current);
	int verticalDifference = this.target.getVerticalDifference(current);
	
	if(this.target.equals(current)) {
	    return;
	}
	else if(horizontalDifference == 0) {
	    buildVerticalMovementPlan(orientation, verticalDifference);
	}
	else if(verticalDifference == 0) {
	    buildHorizontalMovementPlan(orientation, horizontalDifference);
	}
	else {
	    buildGenericPlan(orientation, horizontalDifference, verticalDifference);
	}
    }
    
    private void buildVerticalMovementPlan(Orientation orientation, int verticalDifference) {	
	Orientation o = verticalDifference > 0 ? Orientation.SOUTH : Orientation.NORTH;
	buildStraightMovement(orientation, o, verticalDifference);
    }

    private void buildStraightMovement(Orientation orientation, Orientation o, int difference) {
	int numberOfTurnLeft = orientation.getDifferenceIn90DegreesCCWFrom(o);
	buildStraightMovement(numberOfTurnLeft, difference);
    }

    private void buildStraightMovement(int numberOfTurnLeft, int difference) {
	if(numberOfTurnLeft == -1) {
	    this.plan.enqueueAction(new VacuumWorldTurnRightAction());
	}
	else {
	    addTurnLeftActions(numberOfTurnLeft);
	}
	
	addMoveActions(difference);
    }

    private void addMoveActions(int verticalDifference) {
	for(int i = 0; i < verticalDifference; i++) {
	    this.plan.enqueueAction(new VacuumWorldMoveAction());
	}
    }

    private void addTurnLeftActions(int numberOfTurnLeft) {
	for(int i = 0; i < numberOfTurnLeft; i++) {
	    this.plan.enqueueAction(new VacuumWorldTurnLeftAction());
	}
    }

    private void buildHorizontalMovementPlan(Orientation orientation, int horizontalDifference) {
	Orientation o = horizontalDifference > 0 ? Orientation.EAST : Orientation.WEST;
	buildStraightMovement(orientation, o, horizontalDifference);
    }

    private void buildGenericPlan(Orientation orientation, int horizontalDifference, int verticalDifference) {
	Orientation ho = horizontalDifference > 0 ? Orientation.EAST : Orientation.WEST;
	int hOffset = orientation.getDifferenceIn90DegreesCCWFrom(ho);
	
	Orientation vo = verticalDifference > 0 ? Orientation.SOUTH : Orientation.NORTH;
	int vOffset = orientation.getDifferenceIn90DegreesCCWFrom(vo);
	
	buildGenericPlan(orientation, hOffset, vOffset, horizontalDifference, verticalDifference);
    }

    private void buildGenericPlan(Orientation orientation, int hOffset, int vOffset, int horizontalDifference, int verticalDifference) {
	if(Math.abs(hOffset) > Math.abs(vOffset)) {
	    buildStraightMovement(vOffset, verticalDifference);
	    buildHorizontalMovementPlan(orientation, horizontalDifference); //I call this because hOffset might have changed
	}
	else {
	    buildStraightMovement(hOffset, horizontalDifference);
	    buildVerticalMovementPlan(orientation, verticalDifference); //I call this because vOffset might have changed
	}
    }

    public void rebuildPlan(VacuumWorldCoordinates current, Orientation orientation) {
	buildPlan(current, orientation);
    }
    
    @Override
    public GoToPositionPlan getPlan() {
	return this.plan;
    }

    @Override
    public boolean isCompleted(Object... current) {
	if(current.length == 0) {
	    return false;
	}
	else {
	    return checkCompletion(current[0]);
	}
    }

    private boolean checkCompletion(Object object) {
	if(!VacuumWorldCoordinates.class.isAssignableFrom(object.getClass())) {
	    return false;
	}
	
	return this.target.equals((VacuumWorldCoordinates) object);
    }

    @Override
    public boolean hasAtLeastOnePlan() {
	return this.plan != null;
    }
}