package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEvent;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;
import uk.ac.rhul.cs.dice.vacuumworld.perception.StopPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;

public class VacuumWorldCleaningAgent extends AbstractAgent implements VacuumWorldActor {
	private static final long serialVersionUID = -7231158706838196637L;
	private transient Socket socketWithEnvironment;
	private transient ObjectInputStream input;
	private transient ObjectOutputStream output;
	private volatile boolean stop;
	private volatile boolean pause;
	private boolean simulatedRun;

	public VacuumWorldCleaningAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
		super(id, appearance, sensors, actuators, mind);
	}

	public VacuumWorldCleaningAgent(VacuumWorldCleaningAgent toCopy) {
		this(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
	}

	@Override
	public Socket getSocketWithEnvironment() {
		return this.socketWithEnvironment;
	}

	@Override
	public void setSocketWithEnvironment(Socket socket) {
		this.socketWithEnvironment = socket;
	}

	@Override
	public void setStopFlag(boolean stop) {
		this.stop = stop;
	}

	@Override
	public void setPauseFlag(boolean pause) {
		this.pause = pause;
	}

	@Override
	public boolean isPaused() {
		return this.pause;
	}

	@Override
	public void setRunFlag(boolean simulatedRun) {
		this.simulatedRun = simulatedRun;
	}

	@Override
	public void run() {
		try {
			if (this.simulatedRun) {
				testRun();
			} else {
				realRun();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			
		}
	}

	private void realRun() {
		while (!this.stop) {
			LogUtils.log(getID() + " is being executed.");
			VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getMind().decide();
			getMind().execute((Action<?>) action);
			setForMind(sendToEnvironment(action));
			sendToMind();
		}
	}

	private void testRun() {
		while (!this.stop) {
			LogUtils.log(getID() + " is being executed.");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		LogUtils.log(getID() + ": stop!");
	}

	private Set<Analyzable> sendToEnvironment(VacuumWorldAbstractAction action) {
		try {
			VacuumWorldEvent event = new VacuumWorldEvent(action);
			this.output.writeObject(event);
			this.output.flush();

			return collectEnviromentFeedback();
		} catch (Exception e) {
			LogUtils.log(e);

			return Collections.emptySet();
		}
	}

	private Set<Analyzable> collectEnviromentFeedback() {
		try {
			return collectPerceptions();
		} catch (IOException e) {
			LogUtils.log(getID() + ": stop.");
			LogUtils.fakeLog(e);
			this.stop = true;

			return Collections.emptySet();
		} catch (Exception e) {
			throw new VacuumWorldRuntimeException(e);
		}
	}

	private Set<Analyzable> collectPerceptions() throws ClassNotFoundException, IOException {
		Set<Analyzable> perceptions = new HashSet<>();
		Perception candidate;

		do {
			LogUtils.log(getID() + ": waiting for perception.");
			candidate = (Perception) this.input.readObject();
			checkStop(candidate);
			LogUtils.log(getID() + ": got perception: " + candidate.getClass().getSimpleName() + ".");
			perceptions.add(candidate);
		} while (!(candidate instanceof VacuumWorldPerception));

		return perceptions;
	}

	private void checkStop(Perception candidate) throws IOException {
		if (candidate instanceof StopPerception) {
			throw new IOException();
		}
	}

	@Override
	public VacuumWorldActorAppearance getAppearance() {
		return (VacuumWorldActorAppearance) super.getAppearance();
	}

	@Override
	public ObjectInputStream getInputChannels() {
		return this.input;
	}

	@Override
	public ObjectOutputStream getOutputChannels() {
		return this.output;
	}

	@Override
	public void setInputChannels(ObjectInputStream input) {
		this.input = input;
	}

	@Override
	public void setOutputChannels(ObjectOutputStream output) {
		this.output = output;
	}
}