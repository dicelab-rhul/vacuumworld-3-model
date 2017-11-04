package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;

public abstract class VacuumWorldAbstractCommunicativeAction extends VacuumWorldAbstractAction implements PhysicalAction<VacuumWorldCommunicativeActionsEnum> {
    private VacuumWorldCommunicativeActionsEnum type;
    
    public VacuumWorldAbstractCommunicativeAction(VacuumWorldCommunicativeActionsEnum type) {
	this.type = type;
    }
    
    @Override
    public VacuumWorldCommunicativeActionsEnum getType() {
	return this.type;
    }
    
    @Override
    public Result attempt(Environment context, Physics physics) {
	if(isPossible(context, physics)) {
	    return performAndCheckResult(context, physics);
	}
	else {
	    return new VacuumWorldCommunicativeActionResult(ActionResult.IMPOSSIBLE);
	}
    }
    
    private Result performAndCheckResult(Environment context, Physics physics) {
	Result result = perform(context, physics);
	
	if(ActionResult.FAILURE.equals(result.getActionResultType()) || !succeeded(context, physics)) {
	    return new VacuumWorldCommunicativeActionResult(ActionResult.FAILURE);
	}
	else {
	    return result;
	}
    }
}