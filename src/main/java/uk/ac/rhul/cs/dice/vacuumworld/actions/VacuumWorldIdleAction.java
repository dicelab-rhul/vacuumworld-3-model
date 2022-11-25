package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldSensingActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.physics.VacuumWorldPhysicsInterface;

public class VacuumWorldIdleAction extends VacuumWorldAbstractSensingAction {
    private static final long serialVersionUID = -8335780880947142634L;

    public VacuumWorldIdleAction() {
        super(VacuumWorldSensingActionsEnum.STAY_IDLE);
    }

    @Override
    public boolean isTypeConsistent() {
        return VacuumWorldSensingActionsEnum.STAY_IDLE.equals(getType());
    }

    @Override
    public boolean isPossible(Environment context, Physics physics) {
        if (context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).isPossible(this, (VacuumWorldEnvironment) context);
        }

        return false;
    }

    @Override
    public Result perform(Environment context, Physics physics) {
        if (context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).perform(this, (VacuumWorldEnvironment) context);
        }

        return new VacuumWorldSensingActionResult(ActionResult.FAILURE);
    }

    @Override
    public boolean succeeded(Environment context, Physics physics) {
        if (context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).succeeded(this, (VacuumWorldEnvironment) context);
        }

        return false;
    }
}
