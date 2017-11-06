package uk.ac.rhul.cs.dice.vacuumworld.perception;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;

public class VacuumWorldPerception implements Perception {
    private static final long serialVersionUID = 8241773885032403631L;
    private ActionResult result;
    private VacuumWorldEnvironmentAppearance appearance;
    
    public VacuumWorldPerception(ActionResult result, VacuumWorldEnvironmentAppearance appearance) {
	this.result = result;
	this.appearance = appearance;
    }
    
    public ActionResult getResult() {
	return this.result;
    }
    
    public VacuumWorldEnvironmentAppearance getAppearance() {
	return this.appearance;
    }
}