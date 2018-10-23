package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.util.HashSet;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.CommunicativeAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;

public abstract class VacuumWorldAbstractCommunicativeAction extends VacuumWorldAbstractAction implements CommunicativeAction<VacuumWorldCommunicativeActionsEnum> {
    private static final long serialVersionUID = 1412057176765234760L;
    private VacuumWorldCommunicativeActionsEnum type;
    private VacuumWorldMessage message;
    private Set<String> recipientsIDs;

    public VacuumWorldAbstractCommunicativeAction(VacuumWorldCommunicativeActionsEnum type, VacuumWorldMessage message, Set<String> recipientsIDs) {
	this.type = type;
	this.message = message;
	this.recipientsIDs = recipientsIDs == null ? new HashSet<>() : recipientsIDs;
    }

    @Override
    public VacuumWorldCommunicativeActionsEnum getType() {
	return this.type;
    }

    public VacuumWorldMessage getMessage() {
	return this.message;
    }

    @Override
    public Set<String> getRecipientsIds() {
	return this.recipientsIDs;
    }

    protected void addAllRecipients(Set<String> recipients) {
	this.recipientsIDs = recipients;
    }

    @Override
    public Result attempt(Environment context, Physics physics) {
	if (isPossible(context, physics)) {
	    return performAndCheckResult(context, physics);
	}
	else {
	    return new VacuumWorldCommunicativeActionResult(ActionResult.IMPOSSIBLE, null, new HashSet<>());
	}
    }

    private Result performAndCheckResult(Environment context, Physics physics) {
	try {
	    return performAndCheckResultHelper(context, physics);
	}
	catch (Exception e) {
	    LogUtils.log(e);
	    return new VacuumWorldCommunicativeActionResult(ActionResult.FAILURE, null, new HashSet<>());
	}
    }

    private Result performAndCheckResultHelper(Environment context, Physics physics) {
	Result result = perform(context, physics);

	if (ActionResult.FAILURE.equals(result.getActionResultType()) || !succeeded(context, physics)) {
	    return new VacuumWorldCommunicativeActionResult(ActionResult.FAILURE, null, new HashSet<>());
	}
	else {
	    return result;
	}
    }
}