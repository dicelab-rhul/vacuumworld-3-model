package uk.ac.rhul.cs.dice.vacuumworld;

import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSpeakAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;

public class VacuumWorldPhysics implements Physics, VacuumWorldPhysicsInterface {

    @Override
    public boolean isPossible(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldSpeakAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldSensingAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPossible(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldSpeakAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldBroadcastingAction action,
	    VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldSensingAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public VacuumWorldPhysicalActionResult perform(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean succeeded(VacuumWorldMoveAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldCleanAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldSpeakAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldSensingAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean succeeded(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context) {
	// TODO Auto-generated method stub
	return false;
    }
}