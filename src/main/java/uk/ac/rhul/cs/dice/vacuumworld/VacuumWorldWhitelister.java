package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.cloudstrife9999.logutilities.LogUtils;

import com.google.common.collect.ImmutableMap;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractActuator;
import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractSensor;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.enums.SensorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.ActorAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.CommunicativeAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.EnvironmentalAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.SensingAction;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Event;
import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractCoordinates;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Coordinates;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractActionInterface;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractCommunicativeAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractPhysicalAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldDropDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSpeakAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldCommunicativeActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldSensingActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessageInterface;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActuator;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldSensor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAutonomousActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldMindAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldPrincipalListenerAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.perception.NothingMoreIncomingPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.StopPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldGridPerceptionInterface;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public class VacuumWorldWhitelister {

    private VacuumWorldWhitelister() {}

    public static void whitelistEventClasses(ValidatingObjectInputStream is) {
        if (is == null) {
            LogUtils.log("The ValidatingInputStream passed as parameter is null. Cannot whitelist anything...");
        }
        else {
            is.accept("uk.ac.rhul.cs.dice.*");
            is.accept("org.cloudstrife9999.*");
            is.accept("java.lang.*");
            is.accept("java.util.*");
            is.accept("[Ljava.lang.*");
            is.accept("[Ljava.util.*");
            is.accept(VacuumWorldEvent.class);
            is.accept(VacuumWorldEventInterface.class);
            is.accept(Serializable.class);
            is.accept(Event.class);
            is.accept(Long.class);
            is.accept(VacuumWorldAbstractAction.class);
            is.accept(VacuumWorldAbstractActionInterface.class);
            is.accept(Action.class);
            is.accept(EnvironmentalAction.class);
            is.accept(PhysicalAction.class);
            is.accept(CommunicativeAction.class);
            is.accept(SensingAction.class);
            is.accept(VacuumWorldAbstractPhysicalAction.class);
            is.accept(VacuumWorldPhysicalActionsEnum.class);
            is.accept(Enum.class);
            is.accept(String.class);
            is.accept(VacuumWorldAbstractCommunicativeAction.class);
            is.accept(VacuumWorldCommunicativeActionsEnum.class);
            is.accept(VacuumWorldAbstractSensingAction.class);
            is.accept(VacuumWorldSensingActionsEnum.class);
            is.accept(Set.class);
            is.accept(uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage.class);
            is.accept(VacuumWorldMessageInterface.class);
            is.accept(VacuumWorldMessage.class);
            is.accept(StringBuilder.class);
            is.accept(VacuumWorldMoveAction.class);
            is.accept(VacuumWorldTurnAction.class);
            is.accept(VacuumWorldCoordinates.class);
            is.accept(AbstractCoordinates.class);
            is.accept(Coordinates.class);
            is.accept(Integer.class);
            is.accept(TurnDirection.class);
            is.accept(VacuumWorldTurnLeftAction.class);
            is.accept(VacuumWorldTurnRightAction.class);
            is.accept(Orientation.class);
            is.accept(VacuumWorldCleanAction.class);
            is.accept(VacuumWorldDirtColor.class);
            is.accept(Random.class);
            is.accept(VacuumWorldDropDirtAction.class);
            is.accept(VacuumWorldIdleAction.class);
            is.accept(VacuumWorldSpeakAction.class);
            is.accept(VacuumWorldBroadcastingAction.class);
            is.accept("[C");
            is.accept(HashSet.class);
            is.accept("java.util.Map$KeySet");
            is.accept("java.util.HashMap$KeySet");
        }
    }

    public static void whitelistPerceptionClasses(ValidatingObjectInputStream is) {
        if (is == null) {
            LogUtils.log("The ValidatingInputStream passed as parameter is null. Cannot whitelist anything...");
        }
        else {
            is.accept("uk.ac.rhul.cs.dice.*");
            is.accept("org.cloudstrife9999.*");
            is.accept("java.lang.*");
            is.accept("java.util.*");
            is.accept("[Ljava.lang.*");
            is.accept("[Ljava.util.*");
            is.accept(VacuumWorldMessageInterface.class);
            is.accept(VacuumWorldMessage.class);
            is.accept(StringBuilder.class);
            is.accept(VacuumWorldPerception.class);
            is.accept(Perception.class);
            is.accept(VacuumWorldSpeechPerception.class);
            is.accept(NothingMoreIncomingPerception.class);
            is.accept(StopPerception.class);
            is.accept(String.class);
            is.accept(Long.class);
            is.accept(ActionResult.class);
            is.accept(Enum.class);
            is.accept(Analyzable.class);
            is.accept(Serializable.class);
            is.accept(VacuumWorldActorAppearance.class);
            is.accept(ActorAppearance.class);
            is.accept(AvatarAppearance.class);
            is.accept(Appearance.class);
            is.accept(AgentColor.class);
            is.accept(ActorType.class);
            is.accept(Orientation.class);
            is.accept(Sensor.class);
            is.accept(Actuator.class);
            is.accept(AbstractSensor.class);
            is.accept(AbstractActuator.class);
            is.accept(VacuumWorldSensor.class);
            is.accept(VacuumWorldActuator.class);
            is.accept(ActuatorPurposeEnum.class);
            is.accept(SensorPurposeEnum.class);
            is.accept(VacuumWorldMindAppearance.class);
            is.accept(Integer.class);
            is.accept(VacuumWorldPrincipalListenerAppearance.class);
            is.accept(VacuumWorldEnvironmentAppearance.class);
            is.accept(Map.class);
            is.accept(VacuumWorldCoordinates.class);
            is.accept(AbstractCoordinates.class);
            is.accept(Coordinates.class);
            is.accept(ImmutableMap.class);
            is.accept("com.google.common.collect.ImmutableMap$SerializedForm");
            is.accept(EnvironmentAppearance.class);
            is.accept(VacuumWorldGridPerceptionInterface.class);
            is.accept(VacuumWorldLocationAppearance.class);
            is.accept(Comparable.class);
            is.accept(Boolean.class);
            is.accept(VacuumWorldDirtAppearance.class);
            is.accept(VacuumWorldDirtColor.class);
            is.accept("[Ljava.lang.Object;");
            is.accept(Number.class);
            is.accept(VacuumWorldAutonomousActorAppearance.class);
            is.accept("com.google.common.collect.ImmutableList$SerializedForm");
            is.accept("[C");
            is.accept(Set.class);
            is.accept(HashSet.class);
            is.accept("java.util.Map$KeySet");
            is.accept("java.util.HashMap$KeySet");
        }
    }
}
