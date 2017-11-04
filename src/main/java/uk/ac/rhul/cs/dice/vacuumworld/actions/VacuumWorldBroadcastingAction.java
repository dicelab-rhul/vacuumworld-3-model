package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldPhysicsInterface;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;

public class VacuumWorldBroadcastingAction extends VacuumWorldSpeakAction {

    public VacuumWorldBroadcastingAction() {
	super(VacuumWorldCommunicativeActionsEnum.BROADCAST);
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldCommunicativeActionsEnum.BROADCAST.equals(getType());
    }

    @Override
    public boolean isPossible(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).isPossible(this, (VacuumWorldEnvironment) context);
        }
        
        return false;
    }

    @Override
    public Result perform(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).perform(this, (VacuumWorldEnvironment) context);
        }
        
        return new VacuumWorldCommunicativeActionResult(ActionResult.FAILURE);
    }
    
    @Override
    public boolean succeeded(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).succeeded(this, (VacuumWorldEnvironment) context);
        }
        
        return false;
    }
}