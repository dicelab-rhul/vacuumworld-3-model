package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;

public class VacuumWorldUserMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 2345235975881410062L;

    public VacuumWorldUserMind(String bodyId) {
	super(bodyId);
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	switch (getRng().nextInt(5)) {
	case 0:
	    return new VacuumWorldTurnLeftAction();
	case 1:
	    return new VacuumWorldTurnRightAction();
	case 2:
	    return new VacuumWorldMoveAction();
	case 3:
	    return new VacuumWorldDropDirtAction(VacuumWorldDirtColor.ORANGE);
	case 4:
	    return new VacuumWorldDropDirtAction(VacuumWorldDirtColor.GREEN);
	default:
	    return new VacuumWorldSensingAction();
	}
    }
}