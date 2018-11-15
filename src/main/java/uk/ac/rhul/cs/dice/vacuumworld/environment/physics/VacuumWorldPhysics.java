package uk.ac.rhul.cs.dice.vacuumworld.environment.physics;

import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSpeakAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldPhysics implements VacuumWorldPhysicsInterface {

    @Override
    public synchronized boolean isPossible(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldActor actor = context.getActorFromId(actorID);
	VacuumWorldLocation currentLocation = context.getLocationFromActorId(actorID);

	return actor != null && context.checkTargetLocation(currentLocation.getCoordinates(), context.getOrientation(actor));
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldActor actor = context.getActorFromId(actorID);
	VacuumWorldLocation currentLocation = context.getLocationFromActorId(actorID);

	return actor instanceof VacuumWorldCleaningAgent && currentLocation.isCleanableBy(actor.getAppearance().getColor());
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldSpeakAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldIdleAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldActor actor = context.getActorFromId(actorID);
	VacuumWorldLocation currentLocation = context.getLocationFromActorId(actorID);

	return actor instanceof VacuumWorldUserAgent && !currentLocation.containsDirt();

    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldLocation actorLocation = context.getLocationFromActorId(actorID);

	if (actorLocation == null) {
	    throw new UnsupportedOperationException("The actor is nowhere to be found!");
	}
	else {
	    action.setOriginalCoordinates(actorLocation.getCoordinates());
	    context.moveActor(actorID);

	    return new VacuumWorldPhysicalActionResult(ActionResult.SUCCESS);
	}
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context) {
	return performTurn(action, context);
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	return performTurn(action, context);
    }

    private VacuumWorldPhysicalActionResult performTurn(VacuumWorldTurnAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldActor actor = context.getActorFromId(actorID);

	if (actor == null) {
	    throw new UnsupportedOperationException("The actor is null!");
	}
	else {
	    action.setOriginalOrientation(context.getOrientation(actor));
	    actor.turn(action.getTurnDirection());

	    return new VacuumWorldPhysicalActionResult(ActionResult.SUCCESS);
	}
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldLocation actorLocation = context.getLocationFromActorId(actorID);
	VacuumWorldDirt dirt = actorLocation.removeDirt();

	if (dirt == null) {
	    throw new UnsupportedOperationException("There is no dirt to clean!");
	}
	else {
	    action.setCleanedDirtColor(dirt.getAppearance().getColor());

	    return new VacuumWorldPhysicalActionResult(ActionResult.SUCCESS);
	}
    }

    @Override
    public synchronized VacuumWorldCommunicativeActionResult perform(VacuumWorldSpeakAction action, VacuumWorldEnvironment context) {
	return new VacuumWorldCommunicativeActionResult(ActionResult.SUCCESS, action.getMessage(), action.getRecipientsIds());
    }

    @Override
    public synchronized VacuumWorldCommunicativeActionResult perform(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context) {
	Set<String> recipients = context.getGridReadOnly().entrySet().stream().filter(e -> e.getValue().containsAnActorDifferentFrom(action.getActorID())).map(e -> e.getValue().getActorIfAny()).map(VacuumWorldActor::getID).collect(Collectors.toSet());
	action.addRecipients(recipients);
	
	return new VacuumWorldCommunicativeActionResult(ActionResult.SUCCESS, action.getMessage(), action.getRecipientsIds());
    }

    @Override
    public synchronized VacuumWorldSensingActionResult perform(VacuumWorldIdleAction action, VacuumWorldEnvironment context) {
	return new VacuumWorldSensingActionResult(ActionResult.SUCCESS);
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldActor actor = context.getActorFromId(actorID);
	VacuumWorldLocation actorLocation = context.getLocationFromActorId(actorID);

	if (!(actor instanceof VacuumWorldUserAgent) || actorLocation.containsDirt()) {
	    throw new UnsupportedOperationException("Dropping dirt is impossible!");
	}
	else {
	    actorLocation.addDirt(new VacuumWorldDirt(action.getDroppedDirtColor()));

	    return new VacuumWorldPhysicalActionResult(ActionResult.SUCCESS);
	}
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	VacuumWorldLocation current = context.getLocationFromActorId(actorId);

	return context.checkOldLocation(action.getOriginalCoordinates(), actorId) && current.containsSuchActor(actorId);
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context) {
	return turnSucceeded(action, context);
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	return turnSucceeded(action, context);
    }

    private boolean turnSucceeded(VacuumWorldTurnAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	VacuumWorldActor actor = context.getActorFromId(actorId);
	Orientation expected = getExpectedOrientation(action);

	return actor != null && expected.equals(actor.getAppearance().getOrientation());
    }

    private Orientation getExpectedOrientation(VacuumWorldTurnAction action) {
	Orientation base = action.getOriginalOrientation();

	switch (action.getTurnDirection()) {
	case LEFT:
	    return base.getLeft();
	case RIGHT:
	    return base.getRight();
	default:
	    throw new IllegalArgumentException();
	}
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	VacuumWorldLocation current = context.getLocationFromActorId(actorId);

	return action.getCleanedDirtColor().canBeCleanedBy(context.getActorFromId(actorId).getAppearance().getColor()) && !current.containsDirt();
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldSpeakAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public boolean succeeded(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public boolean succeeded(VacuumWorldIdleAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public boolean succeeded(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	VacuumWorldLocation current = context.getLocationFromActorId(actorId);

	return current.containsDirtOfColor(action.getDroppedDirtColor());
    }
}