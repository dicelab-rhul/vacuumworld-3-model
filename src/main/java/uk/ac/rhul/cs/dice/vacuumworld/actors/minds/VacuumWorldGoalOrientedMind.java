package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.Set;

import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.advanced.GoToPositionGoal;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;

public class VacuumWorldGoalOrientedMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = -994856299287493197L;
    private transient GoToPositionGoal goal;
    private VacuumWorldActorAppearance self;
    private VacuumWorldCoordinates currentCoordinates;
    
    public VacuumWorldGoalOrientedMind(String bodyId) {
	super(bodyId);
    }
    
    @Override
    public void perceive(Set<Analyzable> perceptions) {
        super.perceive(perceptions);
        
        this.self = getPerception().getAppearance().getActorAppearance(getBodyId());
        this.currentCoordinates = getPerception().getAppearance().getLocationFromActiveBodyId(getBodyId()).getCoordinates();
    }
    
    @Override
    public VacuumWorldAbstractAction decide() {
	if(!this.goal.isCompleted(this.currentCoordinates)) {
	    return getCloserToGoal();
	}
	else {
	    return decideRandomly();
	}
    }

    private VacuumWorldAbstractAction getCloserToGoal() {
	if(!this.goal.hasAtLeastOnePlan() || this.goal.getPlan().isEmpty()) {
	    return decideRandomly();
	}
	else {
	    return (VacuumWorldAbstractAction) this.goal.getPlan().pop();
	}
    }
    
    @Override
    public void revise() {	
	if(this.goal == null || this.goal.isCompleted(this.currentCoordinates)) {
	    //we really want to move back to (0, 0) if the agent has moved.
	    this.goal = new GoToPositionGoal(new VacuumWorldCoordinates(0, 0));
            this.goal.buildPlan(this.currentCoordinates, this.self.getOrientation());
	}
    }
}