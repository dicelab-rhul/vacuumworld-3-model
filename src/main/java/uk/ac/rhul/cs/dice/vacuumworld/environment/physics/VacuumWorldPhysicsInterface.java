package uk.ac.rhul.cs.dice.vacuumworld.environment.physics;

import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSpeakAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public interface VacuumWorldPhysicsInterface extends Physics {
    public abstract boolean isPossible(VacuumWorldMoveAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldCleanAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldSpeakAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldIdleAction action, VacuumWorldEnvironment context);

    public abstract boolean isPossible(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldPhysicalActionResult perform(VacuumWorldMoveAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldPhysicalActionResult perform(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldPhysicalActionResult perform(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldPhysicalActionResult perform(VacuumWorldCleanAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldCommunicativeActionResult perform(VacuumWorldSpeakAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldCommunicativeActionResult perform(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldSensingActionResult perform(VacuumWorldIdleAction action, VacuumWorldEnvironment context);

    public abstract VacuumWorldPhysicalActionResult perform(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldMoveAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldTurnLeftAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldTurnRightAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldCleanAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldSpeakAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldBroadcastingAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldIdleAction action, VacuumWorldEnvironment context);

    public abstract boolean succeeded(VacuumWorldDropDirtAction action, VacuumWorldEnvironment context);
}
