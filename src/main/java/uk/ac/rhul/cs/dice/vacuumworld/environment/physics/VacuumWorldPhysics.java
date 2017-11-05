package uk.ac.rhul.cs.dice.vacuumworld.environment.physics;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSpeakAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.agents.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agents.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

public class VacuumWorldPhysics implements Physics, VacuumWorldPhysicsInterface {

    @Override
    public synchronized boolean isPossible(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	Actor actor = context.getActorFromId(actorID);
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
	Actor actor = context.getActorFromId(actorID);
	VacuumWorldLocation currentLocation = context.getLocationFromActorId(actorID);
	
	return actor != null && actor instanceof VacuumWorldCleaningAgent && currentLocation.isCleanableBy(((VacuumWorldActorAppearance) ((VacuumWorldCleaningAgent) actor).getAppearance()).getColor());
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
    public synchronized boolean isPossible(VacuumWorldSensingAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public synchronized boolean isPossible(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	Actor actor = context.getActorFromId(actorID);
	VacuumWorldLocation currentLocation = context.getLocationFromActorId(actorID);
	
	return actor != null && actor instanceof VacuumWorldUserAgent && !currentLocation.containsDirt();
    
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldLocation actorLocation = context.getLocationFromActorId(actorID);
	
	if(actorLocation == null) {
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
	String actorID = action.getActorID();
	Actor actor = context.getActorFromId(actorID);
	
	if(actor == null) {
	    throw new UnsupportedOperationException("The actor is null!");
	}
	else {
	    action.setOriginalOrientation(context.getOrientation(actor));
	    actor.turnLeft();
	    
	    return new VacuumWorldPhysicalActionResult(ActionResult.SUCCESS);
	}
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	Actor actor = context.getActorFromId(actorID);
	
	if(actor == null) {
	    throw new UnsupportedOperationException("The actor is null!");
	}
	else {
	    action.setOriginalOrientation(context.getOrientation(actor));
	    actor.turnRight();
	    
	    return new VacuumWorldPhysicalActionResult(ActionResult.SUCCESS);
	}
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	VacuumWorldLocation actorLocation = context.getLocationFromActorId(actorID);
	VacuumWorldDirt dirt = actorLocation.removeDirt();
	
	if(dirt == null) {
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
	return new VacuumWorldCommunicativeActionResult(ActionResult.SUCCESS, action.getMessage(), action.getRecipientsIds());
    }

    @Override
    public synchronized VacuumWorldSensingActionResult perform(VacuumWorldSensingAction action, VacuumWorldEnvironment context) {
	return new VacuumWorldSensingActionResult(ActionResult.SUCCESS);
    }

    @Override
    public synchronized VacuumWorldPhysicalActionResult perform(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	String actorID = action.getActorID();
	Actor actor = context.getActorFromId(actorID);
	VacuumWorldLocation actorLocation = context.getLocationFromActorId(actorID);
	
	if(!(actor instanceof VacuumWorldUserAgent) || actorLocation.containsDirt()) {
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
	String actorId = action.getActorID();
	Actor actor = context.getActorFromId(actorId);
	
	return actor != null && action.getOriginalOrientation().getLeft().equals(((VacuumWorldActorAppearance) ((AbstractAgent) actor).getAppearance()).getOrientation());
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	Actor actor = context.getActorFromId(actorId);
	
	return actor != null && action.getOriginalOrientation().getRight().equals(((VacuumWorldActorAppearance) ((AbstractAgent) actor).getAppearance()).getOrientation());
    }

    @Override
    public synchronized boolean succeeded(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	VacuumWorldLocation current = context.getLocationFromActorId(actorId);
	
	return action.getCleanedDirtColor().canBeCleanedBy(((VacuumWorldActorAppearance) ((AbstractAgent) context.getActorFromId(actorId)).getAppearance()).getColor()) && !current.containsDirt();
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
    public boolean succeeded(VacuumWorldSensingAction action, VacuumWorldEnvironment context) {
	return true;
    }

    @Override
    public boolean succeeded(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	String actorId = action.getActorID();
	VacuumWorldLocation current = context.getLocationFromActorId(actorId);
	
	return current.containsDirtOfColor(action.getDroppedDirtColor());
    }
}