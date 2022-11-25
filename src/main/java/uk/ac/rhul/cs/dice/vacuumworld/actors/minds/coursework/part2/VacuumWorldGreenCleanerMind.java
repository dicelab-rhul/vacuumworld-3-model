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
 * This mind subclasses {@link VacuumWorldColoredCleanerMind}.
 * 
 */
public class VacuumWorldGreenCleanerMind extends VacuumWorldColoredCleanerMind {
    private static final long serialVersionUID = -3848363346194146181L;
    // Add any class attribute you want/need.

    public VacuumWorldGreenCleanerMind(String bodyId) { // Add any additional parameter you need.
        super(bodyId); // Add any additional parameter you need, if the superclass constructor requires more.

        // Edit here if needed.
    }

    public VacuumWorldGreenCleanerMind(String bodyId, int rngLowerLimit, int rngUpperLimit) { // Add any additional parameter you need.
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
