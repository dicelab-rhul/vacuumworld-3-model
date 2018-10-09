package uk.ac.rhul.cs.dice.vacuumworld.perception;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

/**
 * 
 * Wrapper for a {@link VacuumWorldMessage} and the {@link VacuumWorldctorAppearance} of its sender. It implements
 * {@link Perception}.
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldSpeechPerception implements Perception {
	private static final long serialVersionUID = 9175964739556380663L;
	private VacuumWorldMessage message;
	private VacuumWorldActorAppearance sender;

	/**
	 * 
	 * Constructs the {@link VacuumWorldSpeechPerception} with a {@link VacuumWorldMessage} and the
	 * {@link VacuumWorldActorAppearance} of its sender.
	 * 
	 * @param message
	 *            a {@link VacuumWorldMessage}.
	 * @param sender
	 *            the {@link VacuumWorldActorAppearance} of the {@link VacuumWorldMessage} sender.
	 * 
	 */
	public VacuumWorldSpeechPerception(VacuumWorldMessage message, VacuumWorldActorAppearance sender) {
		this.message = message;
		this.sender = sender;
	}

	/**
	 * 
	 * Returns the {@link VacuumWorldMessage}.
	 * 
	 * @return the {@link VacuumWorldMessage}.
	 * 
	 */
	public VacuumWorldMessage getMessage() {
		return this.message;
	}

	/**
	 * 
	 * Returns the {@link VacuumWorldActorAppearance} of the sender of the {@link VacuumWorldMessage}.
	 * 
	 * @return the {@link VacuumWorldActorAppearance} of the sender of the {@link VacuumWorldMessage}.
	 * 
	 */
	public VacuumWorldActorAppearance getSender() {
		return this.sender;
	}

	@Override
	public String toString() {
		return "percept: " + this.message + " from: " + this.sender;
	}
}