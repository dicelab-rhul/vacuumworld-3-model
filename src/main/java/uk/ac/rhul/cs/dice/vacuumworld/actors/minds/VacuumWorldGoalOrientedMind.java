package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.advanced.GoToPositionGoal;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;

public class VacuumWorldGoalOrientedMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = -994856299287493197L;
    private transient GoToPositionGoal goal;
    private VacuumWorldActorAppearance self;
    private VacuumWorldCoordinates currentCoordinates;

    public VacuumWorldGoalOrientedMind(String bodyId, VacuumWorldCoordinates target) {
        super(bodyId);

        this.goal = new GoToPositionGoal(target);
    }

    @Override
    public VacuumWorldAbstractAction decide() {
        if(!this.goal.isCompleted(this.currentCoordinates)) {
            return getCloserToGoal();
        }
        else {
            return decideWithRNG();
        }
    }

    private VacuumWorldAbstractAction getCloserToGoal() {
        if(!this.goal.hasAtLeastOnePlan() || this.goal.getPlan().isEmpty()) {
            return decideWithRNG();
        }
        else {
            return (VacuumWorldAbstractAction) this.goal.getPlan().pop();
        }
    }

    @Override
    public void revise() {
        reviseSelfAttributes();

        if(!this.goal.isCompleted(this.currentCoordinates)) {
            revisePlan();
        }
    }

    private void revisePlan() {
        if(!this.goal.hasAtLeastOnePlan()) {
            this.goal.buildPlan(this.currentCoordinates, this.self.getOrientation());
        }
    }

    private void reviseSelfAttributes() {
        this.self = getAgent();
        this.currentCoordinates = getCoordinates();
    }
}
