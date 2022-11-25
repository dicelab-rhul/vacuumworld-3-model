package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.coursework.part3;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

public class VacuumWorldAbstractAdvancedMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = -4628456942268697709L;

    public VacuumWorldAbstractAdvancedMind(String bodyId) {
        super(bodyId);
        // Edit here if needed.
    }

    public VacuumWorldAbstractAdvancedMind(String bodyId, int rngLowerLimit, int rngUpperLimit) {
        super(bodyId, rngLowerLimit, rngUpperLimit);
        // Edit here if needed.
    }

    @Override
    public void revise() {
        // Edit here if needed.
    }

    @Override
    public VacuumWorldAbstractAction decide() {
        // Edit here if needed.
        return null;
    }
}
