package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.List;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.interfaces.ActorAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;

public interface VacuumWorldActorAppearance extends ActorAppearance, AvatarAppearance {

    public default AgentColor getColor() {
	return AgentColor.UNDEFINED;
    }

    public default boolean isGreenAgent() {
	return isCleaningAgent() && AgentColor.GREEN.equals(getColor());
    }

    public default boolean isOrangeAgent() {
	return isCleaningAgent() && AgentColor.ORANGE.equals(getColor());
    }

    public default boolean isWhiteAgent() {
	return isCleaningAgent() && AgentColor.WHITE.equals(getColor());
    }

    public default boolean isCleaningAgent() {
	return ActorType.CLEANING_AGENT.equals(getType());
    }

    public default boolean isUser() {
	return ActorType.USER.equals(getType());
    }

    public default boolean isAvatar() {
	return ActorType.AVATAR.equals(getType());
    }

    public default void turnLeft() {
	setOrientation(getOrientation().getLeft());
    }

    public default void turnRight() {
	setOrientation(getOrientation().getRight());
    }

    @Override
    public default int getListeningPort() {
	throw new UnsupportedOperationException("Not an Avatar!");
    }

    public abstract ActorType getType();

    public abstract Orientation getOrientation();

    public abstract void setOrientation(Orientation orientation);

    public abstract List<Actuator> getActuators();

    public abstract List<Sensor> getSensors();

    @Override
    public abstract JsonObject serialize();
}