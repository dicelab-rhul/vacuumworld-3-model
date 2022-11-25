package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.coursework.part2;

/**
 * 
 * This is for part 2 of the course-work only.<br/><br/>
 * This class MUST remain abstract.<br/><br/>
 * Think of this class as the common superclass of {@link VacuumWorldGreenCleanerMind} and {@link VacuumWorldOrangeCleanerMind}.<br/><br/>
 * This mind subclasses {@link VacuumWorldAbstractCleanerMind}.
 * 
 */
public abstract class VacuumWorldColoredCleanerMind extends VacuumWorldAbstractCleanerMind {
    private static final long serialVersionUID = 2849444832311084020L;
    // Add any class attribute you want/need.

    protected VacuumWorldColoredCleanerMind(String bodyId) { // Add any additional parameter you need.
        super(bodyId);

        // Edit here if needed.
    }

    protected VacuumWorldColoredCleanerMind(String bodyId, int rngLowerLimit, int rngUpperLimit) { // Add any additional parameter you need.
        super(bodyId, rngLowerLimit, rngUpperLimit);

        // Edit here if needed.
    }

    // Add any method you need.
}
