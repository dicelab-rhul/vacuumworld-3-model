package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.coursework.part2;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/**
 * 
 * This is for part 2 of the course-work only.<br/>
 * <br/>
 * This class is for white agents.<br/>
 * <br/>
 * Do not subclass this class.<br/>
 * <br/>
 * This mind subclasses {@link VacuumWorldAbstractCleanerMind}.
 * 
 */
public class VacuumWorldWhiteCleanerMind extends VacuumWorldAbstractCleanerMind {
    private static final long serialVersionUID = 2760454725661174370L;
    // Add any class attribute you want/need.

    public VacuumWorldWhiteCleanerMind(String bodyId) { // Add any additional parameter you need.
        super(bodyId);

        // Edit here if needed.
    }

    public VacuumWorldWhiteCleanerMind(String bodyId, int rngLowerLimit, int rngUpperLimit) { // Add any additional parameter you need.
        super(bodyId, rngLowerLimit, rngUpperLimit); // Add any additional parameter you need, if the superclass constructor requires more.

        // Edit here if needed.
    }

    @Override
    public VacuumWorldAbstractAction decide() {
        // Edit here.

        // Change this with the desired behavior. Remember: NEVER return null!
        return super.decideWithRNG();
    }

    @Override
    public void revise() {
        // Edit here.
    }

    // Add any method you need.
}
