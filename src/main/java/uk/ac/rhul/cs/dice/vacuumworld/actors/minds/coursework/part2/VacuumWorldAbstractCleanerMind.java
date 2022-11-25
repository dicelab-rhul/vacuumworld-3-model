package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.coursework.part2;

import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

/**
 * 
 * This is for part 2 of the course-work only.<br/><br/>
 * This class MUST remain abstract.<br/><br/>
 * Think of this class as the common superclass of {@link VacuumWorldColoredCleanerMind} and {@link VacuumWorldWhiteCleanerMind}.<br/><br/>
 * This mind subclasses {@link VacuumWorldAbstractMind}.
 * 
 */
public abstract class VacuumWorldAbstractCleanerMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 394002811873638711L;
    // Add any class attribute you want/need.

    protected VacuumWorldAbstractCleanerMind(String bodyId) { // Add any additional parameter you need.
        super(bodyId);

        // Edit here if needed.
    }

    protected VacuumWorldAbstractCleanerMind(String bodyId, int rngLowerLimit, int rngUpperLimit) { // Add any additional parameter you need.
        super(bodyId, rngLowerLimit, rngUpperLimit);

        // Edit here if needed.
    }

    // Add any method you need.
}
