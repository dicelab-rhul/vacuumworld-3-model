package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Avatar;
import uk.ac.rhul.cs.dice.agent.interfaces.PrincipalListener;
import uk.ac.rhul.cs.dice.agentactions.enums.EnvironmentalActionType;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public interface VacuumWorldActor extends Actor, Avatar {

	@Override
	public default void sendFeedbackToPrincipal(Analyzable... feedback) {
		throw new UnsupportedOperationException("Not an Avatar!");
	}

	@Override
	public default PrincipalListener getPrincipalListener() {
		throw new UnsupportedOperationException("Not an Avatar!");
	}

	public default void turn(TurnDirection direction) {
		switch (direction) {
		case LEFT:
			turnLeft();
			break;
		case RIGHT:
			turnRight();
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public default void openSocket(String hostname, int port) throws IOException {
		setSocketWithEnvironment(new Socket(hostname, port));

		ObjectOutputStream o = new ObjectOutputStream(getSocketWithEnvironment().getOutputStream());
		ObjectInputStream i = new ObjectInputStream(getSocketWithEnvironment().getInputStream());
		o.writeUTF(getID());
		o.flush();
		setOutputChannels(o);
		setInputChannels(i);
	}

	@Override
	public default void turnLeft() {
		getAppearance().turnLeft();
	}

	@Override
	public default void turnRight() {
		getAppearance().turnRight();
	}

	@Override
	public default void sendToActuator(Action<?> action) {
		EnvironmentalActionType type = (EnvironmentalActionType) action.getGenericType();
		((VacuumWorldActuator) getActuatorFromActionType(type)).validateExecution(action);
	}

	public default Actuator getActuatorFromActionType(EnvironmentalActionType type) {
		switch (type) {
		case PHYSICAL:
			return getSpecificActuators(ActuatorPurposeEnum.ACT_PHYSICALLY).get(0);
		case COMMUNICATIVE:
			return getSpecificActuators(ActuatorPurposeEnum.SPEAK).get(0);
		case SENSING:
			return getSpecificActuators(ActuatorPurposeEnum.OTHER).get(0);
		default:
			throw new UnsupportedOperationException("No compatible actuator found.");
		}
	}

	@Override
	public default JsonObject serialize() {
		return getAppearance().serialize();
	}

	public abstract VacuumWorldActorAppearance getAppearance();

	public abstract void setStopFlag(boolean stop);

	public abstract void setPauseFlag(boolean pause);

	public abstract void setRunFlag(boolean simulatedRun);

	public abstract void setSocketWithEnvironment(Socket socket);

	public abstract Socket getSocketWithEnvironment();

	public abstract boolean isPaused();
}