package uk.ac.rhul.cs.dice.vacuumworld.actions;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;

public abstract class VacuumWorldAbstractPhysicalAction extends VacuumWorldAbstractAction implements PhysicalAction<VacuumWorldPhysicalActionsEnum> {
    private static final long serialVersionUID = -6157006955598814219L;
    private VacuumWorldPhysicalActionsEnum type;

    public VacuumWorldAbstractPhysicalAction(VacuumWorldPhysicalActionsEnum type) {
	super(type.getCode());
	
	this.type = type;
    }

    @Override
    public VacuumWorldPhysicalActionsEnum getType() {
	return this.type;
    }

    @Override
    public Result attempt(Environment context, Physics physics) {
	if (isPossible(context, physics)) {
	    LogUtils.log(getActorID() + ": " + getClass().getSimpleName() + " is POSSIBLE.");

	    return performAndCheckResult(context, physics);
	}
	else {
	    LogUtils.log(getActorID() + ": " + getClass().getSimpleName() + " is IMPOSSIBLE.");
		
	    return new VacuumWorldPhysicalActionResult(ActionResult.IMPOSSIBLE);
	}
    }

    private Result performAndCheckResult(Environment context, Physics physics) {
	try {
	    return performAndCheckResultHelper(context, physics);
	}
	catch (Exception e) {
	    LogUtils.log(e);

	    return new VacuumWorldPhysicalActionResult(ActionResult.FAILURE);
	}
    }

    private Result performAndCheckResultHelper(Environment context, Physics physics) {
	Result result = perform(context, physics);
	LogUtils.log(getActorID() + ": " + getClass().getSimpleName() + " executed with: " + result.getActionResultType() + ".");
	    

	if (ActionResult.FAILURE.equals(result.getActionResultType()) || !succeeded(context, physics)) {
	    LogUtils.log(getActorID() + ": " + getClass().getSimpleName() + " post-conditions check: FAILURE.");

	    return new VacuumWorldPhysicalActionResult(ActionResult.FAILURE);
	}
	else {
	    LogUtils.log(getActorID() + ": " + getClass().getSimpleName() + " post-conditions check: " + result.getActionResultType() + ".");

	    return result;
	}
    }
}