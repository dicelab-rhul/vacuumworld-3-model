package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.physics.VacuumWorldPhysicsInterface;

public class VacuumWorldSpeakAction extends VacuumWorldAbstractCommunicativeAction {
    private static final long serialVersionUID = 7085737175672939094L;

    public VacuumWorldSpeakAction(VacuumWorldMessage message, Set<String> recipients) {
	super(VacuumWorldCommunicativeActionsEnum.SPEAK, message, recipients);
    }
    
    /**
     * 
     * Do NOT call this constructor directly. Construct a {@link VacuumWorldBroadcastingAction} instead.
     * 
     * @param message a {@link VacuumWorldMessage}.
     * 
     */
    public VacuumWorldSpeakAction(VacuumWorldMessage message) {
	super(VacuumWorldCommunicativeActionsEnum.BROADCAST, message, new HashSet<>());
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldCommunicativeActionsEnum.SPEAK.equals(getType());
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
        
        return new VacuumWorldCommunicativeActionResult(ActionResult.FAILURE, null, new HashSet<>());
    }
    
    @Override
    public boolean succeeded(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).succeeded(this, (VacuumWorldEnvironment) context);
        }
        
        return false;
    }
    
    @Override
    public String toShortString() {
        return "S";
    }
}