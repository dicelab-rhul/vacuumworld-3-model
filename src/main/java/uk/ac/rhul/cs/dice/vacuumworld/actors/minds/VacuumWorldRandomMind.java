package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/**
 * 
 * This very primitive mind does not revise anything, and continuously decides to attempt a random action.
 * 
 */
public class VacuumWorldRandomMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 8611021479667623569L;

    /**
     * 
     * Constructor that calls the super constructor with the same parameter.
     * 
     * @param bodyId the {@link String} ID of the agent.
     * 
     */
    public VacuumWorldRandomMind(String bodyId) {
        super(bodyId);
    }

    /**
     * 
     * This mind continuously continuously decides to attempt a random action.
     * 
     */
    @Override
    public VacuumWorldAbstractAction decide() {
        return decideWithRNG();
    }

    /**
     * 
     * This mind does not revise anything.
     * 
     */
    @Override
    public void revise() {
        // This mind does not revise anything.
    }
}
