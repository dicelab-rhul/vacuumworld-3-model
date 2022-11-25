package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.tutorials;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;

/**
 * 
 * Mind for Tutorial 1. The task is, for a single agent, to find the grid size optimally.<br/><br/>
 * 
 * The overall goal for this mind is to make the discovery of the grid size optimal w.r.t. the number of cycles it takes.
 * 
 * @author Ben Wilkins
 * @author cloudstrife9999, a.k.a. Emanuele Uliana
 *
 */
public class VacuumWorldTutorial1AgentMind extends VacuumWorldAbstractMind {
    // This is needed for serialization. Do not touch it.
    private static final long serialVersionUID = 8029125006026831040L;

    // This will hold a null reference until initialized somewhere (unless you change the constructor).
    private VacuumWorldCoordinates position;

    // This will hold a null reference until initialized somewhere (unless you change the constructor).
    private Orientation orientation;

    // This will be initialized with false (unless you change the constructor).
    private boolean orientationAwayFromOrigin;

    // This will be initialized with false (unless you change the constructor).
    private boolean arrived;

    // This will be automatically initialized with 0 (unless you change the constructor), which is an impossible value for the size.
    private int gridSize;

    //Add any class attribute you want/need.

    /**
     * 
     * Constructor with the body ID.
     * 
     * @param bodyId the ID of this agent.
     * 
     */
    // Add any additional parameter you need to this constructor.
    public VacuumWorldTutorial1AgentMind(String bodyId) {
        super(bodyId);

        // Edit here if needed.
    }

    /**
     * 
     * This method is always automatically called after {@link #perceive()}, and before {@link #decide()}.<br/><br/>
     * 
     * Its intended use is to update the agent's beliefs.<br/><br/>
     * 
     * The overall goal for this mind is to make the discovery of the grid size optimal w.r.t. the number of cycles it takes.
     * 
     */
    @Override
    public void revise() {
        // Updating the stored current position.
        this.position = getCoordinates();

        // Updating the stored current orientation.
        this.orientation = getOrientation();

        // Updating an internal boolean flag telling whether the agent is facing away from origin.
        this.orientationAwayFromOrigin = isOrientationEast() || isOrientationSouth();

        // Updating an internal boolean flag telling whether the agent is ready to discover the grid size.
        this.arrived = checkIfFacingRelevantWall() || checkSideCases();

        // Storing the grid size if the agent is able to discover it. Otherwise the relevant class attribute is left unchanged.
        this.gridSize = this.arrived ? inferGridSize() : this.gridSize;
    }

    /**
     * 
     * This method is always automatically called after {@link #revise()}, and before {@link #execute()}.<br/><br/>
     * 
     * Its intended use is to output the next action for the agent to {@link #execute()}.<br/><br/>
     * 
     * The overall goal for this mind is to make the discovery of the grid size optimal w.r.t. the number of cycles it takes.
     * 
     */
    @Override
    public VacuumWorldAbstractAction decide() {
        // For students: amend this behaviour in order to make it optimal.
        if(this.arrived) {
            // We are done, and we stay idle.
            return new VacuumWorldIdleAction();
        }
        else if(this.orientationAwayFromOrigin) {
            // We are facing EAST or SOUTH. We can move towards higher X or Y values respectively.
            return new VacuumWorldMoveAction();
        }
        else {
            // We are facing NORTH or WEST. It is better to turn.
            return turnAwayFromOrigin();
        }
    }

    private VacuumWorldAbstractAction turnAwayFromOrigin() {
        if(this.orientation == Orientation.NORTH) {
            // We turn from NORTH to EAST.
            return new VacuumWorldTurnRightAction();
        }
        else if(this.orientation == Orientation.WEST) {
            // We turn from WEST to SOUTH.
            return new VacuumWorldTurnLeftAction();
        }
        else {
            // At the moment, this default behaviour is not logically reachable. It is here just for completeness.
            return new VacuumWorldIdleAction();
        }
    }

    private int inferGridSize() {
    // Simply the highest coordinate + 1 (because the coordinates are 0-indexed).
        return Math.max(this.position.getX(), this.position.getY()) + 1;
    }

    private boolean checkIfFacingRelevantWall() {
    // Facing (EAST OR SOUTH) AND having a wall in front.
        return this.orientationAwayFromOrigin && isWallForward();
    }

    private boolean checkSideCases() {
    // Begin horizontally oriented on the bottom row OR begin vertically oriented on the rightmost column.
        return checkBottomRow() || checkRightmostColumn();
    }

    private boolean checkRightmostColumn() {
    // Facing NORTH AND having a wall on the right OR facing SOUTH AND having a wall on the left.
        return isOrientationNorth() && isWallRight() || isOrientationSouth() && isWallLeft();
    }

    private boolean checkBottomRow() {
    // Facing EAST AND having a wall on the right OR facing WEST AND having a wall on the left.
        return isOrientationEast() && isWallRight() || isOrientationWest() && isWallRight();
    }

    //Add any method you need.
}
