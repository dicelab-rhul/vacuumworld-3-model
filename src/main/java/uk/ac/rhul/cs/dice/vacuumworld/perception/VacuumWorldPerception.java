package uk.ac.rhul.cs.dice.vacuumworld.perception;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;

/**
 * 
 * Wrapper for an {@link ActionResult} and a {@link VacuumWorldEnvironmentAppearance}. It implements {@link Perception}.
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldPerception implements Perception {
	private static final long serialVersionUID = 8241773885032403631L;
	private ActionResult result;
	private VacuumWorldEnvironmentAppearance appearance;

	/**
	 * 
	 * Construct a {@link VacuumWorldPerception} with an {@link ActionResult} and a
	 * {@link VacuumWorldEnvironmentAppearance}.
	 * 
	 * @param result
	 *            an {@link ActionResult}.
	 * @param appearance
	 *            a {@link VacuumWorldEnvironmentAppearance}.
	 * 
	 */
	public VacuumWorldPerception(ActionResult result, VacuumWorldEnvironmentAppearance appearance) {
		this.result = result;
		this.appearance = appearance;
	}

	/**
	 * 
	 * Returns the {@link ActionResult}.
	 * 
	 * @return the {@link ActionResult}.
	 * 
	 */
	public ActionResult getResult() {
		return this.result;
	}

	/**
	 * 
	 * Returns the {@link VacuumWorldEnvironmentAppearance}.
	 * 
	 * @return the {@link VacuumWorldEnvironmentAppearance}.
	 * 
	 */
	public VacuumWorldEnvironmentAppearance getAppearance() {
		return this.appearance;
	}

	@Override
	public String toString() {
		return "percept: " + appearance.toString();
	}
}