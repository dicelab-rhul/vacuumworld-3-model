package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

public class VacuumWorldDefaultMind extends AbstractVacuumWorldMind {
    private static final long serialVersionUID = 8611021479667623569L;

    @Override
    public VacuumWorldAbstractAction decide() {
        return super.decideRandomly();
    }
}