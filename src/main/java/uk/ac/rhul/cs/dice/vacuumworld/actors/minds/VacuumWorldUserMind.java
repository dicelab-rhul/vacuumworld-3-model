package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.ArrayDeque;
import java.util.Queue;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.advanced.GoToPositionGoal;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

/**
 * 
 * DO NOT ASSIGN THIS MIND TO AN AGENT.
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldUserMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 2345235975881410062L;
    private boolean verticalPath;
    private boolean away;
    private Queue<VacuumWorldAbstractAction> saved;
    private boolean lastWasPlanned;
    private boolean visitedOrigin;
    private boolean readyToStart;
    private transient GoToPositionGoal goToOrigin;
    private static final int DEFAULT_USER_RNG_LOWER_LIMIT = 0;
    private static final int DEFAULT_USER_RNG_UPPER_LIMIT = 4;

    public VacuumWorldUserMind(String bodyId) {
	super(bodyId, VacuumWorldUserMind.DEFAULT_USER_RNG_LOWER_LIMIT, VacuumWorldUserMind.DEFAULT_USER_RNG_UPPER_LIMIT);
	
	this.verticalPath = false;
	this.away = true;
	this.saved = new ArrayDeque<>();
	this.lastWasPlanned = false;
	this.visitedOrigin = false;
	this.readyToStart = false;
	this.goToOrigin = new GoToPositionGoal(VacuumWorldCoordinates.getOrigin());
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	if(hasPerception()) {
	    return decideWithPerception();
	}
	else {
	    return new VacuumWorldIdleAction();
	}
    }
    
    private VacuumWorldAbstractAction decideWithPerception() {	
	if(!this.visitedOrigin && this.goToOrigin.isCompleted(getCoordinates())) {
	    this.visitedOrigin = true;
	    
	    return decideWithPerception();
	}
	else if(!this.visitedOrigin && this.goToOrigin.getPlan().isEmpty()) {
	    this.goToOrigin.buildPlan(getCoordinates(), getOrientation());
	    
	    return decideWithPerception();
	}
	else if(!this.visitedOrigin) {
	    return this.goToOrigin.getPlan().next(getLatestActionResult());
	}
	else if(this.readyToStart) {
	    return decideWithRNG();
	}
	else if(isAlligned()) {
	    this.readyToStart = true;
	    
	    return decideWithPerception();
	}
	else {
	    return allign();
	}
    }

    private VacuumWorldAbstractAction allign() {
	if(this.verticalPath) {
	    return isOrientationEast() ? new VacuumWorldTurnRightAction() : new VacuumWorldTurnLeftAction();
	}
	else {
	    return isOrientationNorth() ? new VacuumWorldTurnRightAction() : new VacuumWorldTurnLeftAction();
	}
    }

    private boolean isAlligned() {
	return Orientation.EAST.equals(getOrientation()) && !this.verticalPath || Orientation.SOUTH.equals(getOrientation()) && this.verticalPath;
    }

    @Override
    public VacuumWorldAbstractAction decideWithRNG() {
	int nextRandomInt = getRng().nextInt(getIntRngUpperLimit() + 1) + getIntRngLowerLimit();
	
	if(nextRandomInt >= getIntRngLowerLimit() && nextRandomInt <= getIntRngUpperLimit()) {
	    return resumeBehavior();
	}
	else if(nextRandomInt == getIntRngUpperLimit()) {
	    return isDirt() ? resumeBehavior() : new VacuumWorldDropDirtAction(VacuumWorldDirtColor.random());
	}
	else {
	    throw new VacuumWorldRuntimeException("RNG out of bounds, or inconsistent RNG.");
	}
    }

    private VacuumWorldAbstractAction resumeBehavior() {
	if(singleLocation()) {
	    return new VacuumWorldTurnLeftAction();
	}
	else if(!this.saved.isEmpty()) {
	    return followPlan();
	}
	else if(!isWallForward()) {
	    return new VacuumWorldMoveAction();
	}
	else {
	    return turn();
	}
    }

    private VacuumWorldAbstractAction followPlan() {
	this.lastWasPlanned = true;
	    
	return this.saved.peek();
    }

    private VacuumWorldAbstractAction turn() {
	if(this.verticalPath) {
	    return turnWithVerticalPath();
	}
	else {
	    return turnWithHorizontalPath();
	}
    }

    private VacuumWorldAbstractAction turnWithVerticalPath() {
	if(this.away && isOrientationSouth() || !this.away && isOrientationNorth()) {
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.saved.add(new VacuumWorldMoveAction());
	    this.saved.add(new VacuumWorldTurnLeftAction());
	}
	else {
	    this.saved.add(new VacuumWorldTurnRightAction());
	    this.saved.add(new VacuumWorldMoveAction());
	    this.saved.add(new VacuumWorldTurnRightAction());
	}
	
	this.lastWasPlanned = true;
	
	return this.saved.peek();
    }

    private VacuumWorldAbstractAction turnWithHorizontalPath() {
	if(this.away && isOrientationEast() || !this.away && isOrientationWest()) {
	    this.saved.add(new VacuumWorldTurnRightAction());
	    this.saved.add(new VacuumWorldMoveAction());
	    this.saved.add(new VacuumWorldTurnRightAction());
	}
	else {
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.saved.add(new VacuumWorldMoveAction());
	    this.saved.add(new VacuumWorldTurnLeftAction());
	}
	
	this.lastWasPlanned = true;
	
	return this.saved.peek();
    }

    private boolean singleLocation() {
	return isWallForward() && isWallLeft() && isWallRight();
    }

    @Override
    public void revise() {
	if(this.visitedOrigin && this.readyToStart && hasPerception()) {
	   reviseWithPerception(); 
	}
    }

    private void reviseWithPerception() {
	if(singleLocation()) {
	    this.away = true;
	    this.verticalPath = false;
	}
	else if(this.verticalPath) {
	    revisePlan();
	    reviseVerticalPath();
	}
	else {
	    revisePlan();
	    reviseHorizontalPath();
	}
    }

    private void revisePlan() {
	if(this.lastWasPlanned && ActionResult.SUCCESS.equals(getPerception().getResult())) {
	    this.saved.remove();
	    this.lastWasPlanned = false;
	}
    }

    private void reviseHorizontalPath() {
	if(!this.saved.isEmpty()) {
	    return;
	}
	
	if(this.away) {
	    checkForSouthernBorder();
	}
	else {
	    checkForNorthernBorder();
	}
    }

    private void reviseVerticalPath() {
	if(!this.saved.isEmpty()) {
	    return;
	}
	
	if(this.away) {
	    checkForEasternBorder();
	}
	else {
	    checkForWesternBorder();
	}
    }

    private void checkForWesternBorder() {
	if(checkForNorthWestVertically()) {
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.away = true;
	}
    }

    private boolean checkForNorthWestVertically() {
	return getCoordinates().getX() == 0 && getCoordinates().getY() == 0 && isWallForward() && isWallLeft();
    }

    private void checkForEasternBorder() {
	if(checkForNorthEastVertically() || checkForSouthEastVertically()) {
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.away = false;
	}
    }

    private boolean checkForSouthEastVertically() {
	return getCoordinates().getX() != 0 && getCoordinates().getY() != 0 && isWallForward() && isWallLeft();
    }

    private boolean checkForNorthEastVertically() {
	return getCoordinates().getX() != 0 && getCoordinates().getY() == 0 && isWallForward() && isWallRight();
    }

    private void checkForNorthernBorder() {
	if(checkForNorthWestHorizontally()) {
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.away = true;
	}
    }

    private boolean checkForNorthWestHorizontally() {
	return getCoordinates().getX() == 0 && getCoordinates().getY() == 0 && isWallForward() && isWallRight();
    }

    private void checkForSouthernBorder() {
	if(checkForSouthEastHorizontally() || checkForSouthWestHorizontally()) {
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.saved.add(new VacuumWorldTurnLeftAction());
	    this.away = false;
	}
    }

    private boolean checkForSouthEastHorizontally() {
	return getCoordinates().getX() != 0 && getCoordinates().getY() != 0 && isWallForward() && isWallRight();
    }

    private boolean checkForSouthWestHorizontally() {
	return getCoordinates().getX() == 0 && getCoordinates().getY() != 0 && isWallForward() && isWallLeft();
    }
}