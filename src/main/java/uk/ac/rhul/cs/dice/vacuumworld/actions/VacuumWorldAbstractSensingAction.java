package uk.ac.rhul.cs.dice.vacuumworld.actions;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentactions.interfaces.SensingAction;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldSensingActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;

public abstract class VacuumWorldAbstractSensingAction extends VacuumWorldAbstractAction implements SensingAction<VacuumWorldSensingActionsEnum> {
    private static final long serialVersionUID = 497895445426989292L;
    private VacuumWorldSensingActionsEnum type;
    
    public VacuumWorldAbstractSensingAction(VacuumWorldSensingActionsEnum type) {
	super(type.getCode());
	
	this.type = type;
    }
    
    @Override
    public VacuumWorldSensingActionsEnum getType() {
	return this.type;
    }
    
    @Override
    public Result attempt(Environment context, Physics physics) {
	if(isPossible(context, physics)) {
	    return performAndCheckResult(context, physics);
	}
	else {
	    return new VacuumWorldSensingActionResult(ActionResult.IMPOSSIBLE);
	}
    }
    
    private Result performAndCheckResult(Environment context, Physics physics) {
	try {
	    return performAndCheckResultHelper(context, physics);
	}
	catch(Exception e) {
	    LogUtils.log(e);
	    
	    return new VacuumWorldSensingActionResult(ActionResult.FAILURE);
	}
    }
    
    private Result performAndCheckResultHelper(Environment context, Physics physics) {
	Result result = perform(context, physics);
	
	if(ActionResult.FAILURE.equals(result.getActionResultType()) || !succeeded(context, physics)) {
	    return new VacuumWorldSensingActionResult(ActionResult.FAILURE);
	}
	else {
	    return result;
	}
    }
}