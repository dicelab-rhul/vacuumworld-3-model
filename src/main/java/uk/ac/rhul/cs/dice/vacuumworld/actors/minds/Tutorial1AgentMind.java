package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Coordinates;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

/**
 * 
 * Tutorial 1 Mind Task 1. You should make a copy of this mind (keep it for
 * reference) and edit the {@link Tutorial1AgentMind#decide()} method as the
 * task instructs (see the method below).
 * 
 * @author Ben Wilkins
 *
 */
public class Tutorial1AgentMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 8029125006026831040L;
    private Coordinates position = null;

    /**
     * Constructor. You may do some set up steps here, though they are not necessary
     * in for this task.
     * 
     * @param bodyId
     *                   the id of this agent.
     */
    public Tutorial1AgentMind(String bodyId) {
	super(bodyId);
    }

    /**
     * This is the agent's decide method. You should modify the agent's behaviour in
     * this method where indicated. The agent's current behaviour is to: <br>
     * <p>
     * <ul>
     * <li>1. Turn to face {@link Orientation#East East} or {@link Orientation#South
     * South}
     * <li>2. Move forward
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
	// ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****//

	// Think about how to find the edge of the grid in an optimal way (i.e) with the
	// least number of actions. When you have found the wall, indicate this by
	// performing a notable sequence of actions (e.g. a 360 turn), or by printing
	// to the console.

	// hint 1: what orientation should the agent have before moving?
	// hint 2: you can check if a wall is in front of the agent using
	// isWallForward().

	// ***** ***** ***** ***** ***** EDIT HERE ***** ***** ***** ***** *****//

	// It is best to make a copy of this mind and edit there (otherwise a future git
	// update might overwrite what you have done!)

	// FOR STUDENTS: YOUR TASK IS TO CHANGE THIS DEFAULT BEHAVIOUR!

	if (isOrientationEast() || isOrientationSouth()) {
	    return new VacuumWorldMoveAction();
	}
	
	if (isOrientationNorth()) {
	    return new VacuumWorldTurnRightAction();
	}
	
	if (isOrientationWest()) {
	    return new VacuumWorldTurnLeftAction();
	}
	
	return new VacuumWorldIdleAction(); // the default action (which will never happen).
	// ***** ***** ***** ***** ***** **** **** ***** ***** ***** ***** *****//
    }

    /**
     * This method is called before decide, it should be used to update the internal
     * state of the agent. In this case, the {@link Tutorial1AgentMind#position
     * position} of the agent is updated.
     */
    @Override
    public void revise() {
	this.position = getCoordinates();
    }
    
    public Coordinates getPositionCoordinates() {
        return this.position;
    }
}