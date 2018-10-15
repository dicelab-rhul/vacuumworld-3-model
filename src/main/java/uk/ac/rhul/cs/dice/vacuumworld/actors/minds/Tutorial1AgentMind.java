package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

public class Tutorial1AgentMind extends VacuumWorldAbstractMind {

    private static final long serialVersionUID = 8029125006026831040L;

    public Tutorial1AgentMind(String bodyId) {
	super(bodyId);
    }

    /**
     * This is the agents decide method. You should modify the agents behavior in
     * this methods where indicated. The agents current behavior is to: <br>
     * <p>
     * <ul>
     * <li>1. Attempt a sense action on the first cycle step.
     * <li>2. Turn to face {@link Orientation#East East} or {@link Orientation#South
     * South}
     * <li>3. Move forward
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
	// the first step should always be to check if the agent has a perception, unless you do not plan to use it.
	if (hasPerception()) {
	    // ***** ***** ***** ***** ***** EDIT HERE ***** ***** ***** ***** *****//

	    // think about how to find the edge of the grid in an optimal way (i.e) with the
	    // least number of actions.
	    // hint 1: what orientation should the agent have before moving?
	    // hint 2: you can get the current coordinates of the agent by using the method
	    // - getCoordinates()

	    // FOR STUDENTS: YOUR TASK IS TO CHANGE THIS DEFAULT BEHAVIOR!
	    if (isOrientationEast() || isOrientationSouth()) {
		return new VacuumWorldMoveAction();
	    }
	    else if (isOrientationNorth()) {
		return new VacuumWorldTurnRightAction();
	    }
	    else if (isOrientationWest()) {
		return new VacuumWorldTurnLeftAction();
	    }
	    else {
		throw new IllegalStateException("WE ARE NOT FACING ANYWHERE!?!?");
	    }
	    // ***** ***** ***** ***** ***** **** **** ***** ***** ***** ***** *****//
	}
	else {
	    // when the agent has not perceived, do a sense action (this should only happen
	    // on the first cycle step)
	    return new VacuumWorldIdleAction();
	}
    }
    
    @Override
    public void revise() {
	//Edit here.
    }
}