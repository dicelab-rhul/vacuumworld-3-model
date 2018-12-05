package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.tutorials;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

/**
 * 
 * Tutorial 1 Mind Task 1.<br/>
 * You should make a local copy of this mind (keep it for reference).<br/>
 * You have to edit the {@link #decide()} and {@link #revise()} methods as the task instructs (see below).
 * 
 * @author Ben Wilkins
 *
 */
public class VacuumWorldTutorial1AgentMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 8029125006026831040L;
    private VacuumWorldCoordinates position; // This object will hold a null reference until initialized.
    private Orientation orientation;
    private boolean orientationAwayFromOrigin;
    private boolean done;
    //Add any class attribute you want/need.

    /**
     * Constructor. You may do some set up steps here, though they are not necessary in for this task.
     * 
     * @param bodyId the id of this agent.
     */
    public VacuumWorldTutorial1AgentMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId);
	
	//Edit here if needed.
    }
    
    public VacuumWorldCoordinates getPositionCoordinates() {
	return this.position;
    }

    /**
     * This is the agent's decide method. You should modify the agent's behaviour in
     * this method where indicated. The agent's current behaviour is to: <br>
     * <p>
     * <ul>
     * <li>1. Turn to face {@link Orientation#East East} or {@link Orientation#South
     * South}
     * <li>2. Move forward
     * <li>3. Stop at a wall
     * </ul>
     * <p>
     * 
     * @return the next action that this agent should attempt. Choose from:
     *         <p>
     *         <ul>
     *         <li>{@link VacuumWorldIdleAction},
     *         <li>{@link VacuumWorldTurnLeftAction},
     *         <li>{@link VacuumWorldTurnRightAction},
     *         <li>{@link VacuumWorldMoveAction},
     *         <li>{@link VacuumWorldCleanAction}
     *         </ul>
     */
    @Override
    public VacuumWorldAbstractAction decide() {
	// For students: amend this behaviour in order to make it optimal.
	if(this.done) {
	    return new VacuumWorldIdleAction(); // We are done.
	}
	else if(this.orientationAwayFromOrigin) {
	    return new VacuumWorldMoveAction(); // We move towards higher coordinates in one direction.
	}
	else {
	    return turnAwayFromOrigin(); // We have to turn.
	}
    }

    private VacuumWorldAbstractAction turnAwayFromOrigin() {
	if(this.orientation == Orientation.NORTH) {
	    return new VacuumWorldTurnRightAction(); // North -> East
	}
	else if(this.orientation == Orientation.WEST) {
	    return new VacuumWorldTurnLeftAction(); // West -> South
	}
	else {
	    throw new VacuumWorldRuntimeException("Fatal error: inconsistent orientation!");
	}
    }

    /**
     * 
     * This method is always automatically called after perceive, and before decide. Use it to update the agent beliefs.
     * 
     */
    @Override
    public void revise() {
	this.position = getCoordinates();
	this.orientation = getOrientation();
	this.orientationAwayFromOrigin = isOrientationEast() || isOrientationSouth();
	this.done = this.orientationAwayFromOrigin && isWallForward() || checkSideCases();
    }

    private boolean checkSideCases() {
	return checkIfTopRightFacingNorth() || checkBottomLeftFacingWest();
    }

    private boolean checkBottomLeftFacingWest() {
	return this.orientation == Orientation.WEST && isWallLeft();
    }

    private boolean checkIfTopRightFacingNorth() {
	return this.orientation == Orientation.NORTH && isWallRight();
    }
    
    //Add any method you need.
}