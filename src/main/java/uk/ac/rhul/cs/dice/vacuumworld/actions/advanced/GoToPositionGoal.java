package uk.ac.rhul.cs.dice.vacuumworld.actions.advanced;

import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

public class GoToPositionGoal implements VacuumWorldSimplePhysicalGoal<PhysicalAction<VacuumWorldPhysicalActionsEnum>> {
    private VacuumWorldCoordinates target;
    private GoToPositionPlan plan;

    public GoToPositionGoal(VacuumWorldCoordinates target) {
        this.target = target;
        this.plan = new GoToPositionPlan();
    }

    public void buildPlan(VacuumWorldCoordinates current, Orientation orientation) {
        if (!this.target.equals(current)) {
            int hDiff = this.target.getHorizontalDifference(current); // target.x - current.x
            int vDiff = this.target.getVerticalDifference(current); // target.y - current.y

            buildPlan(hDiff, vDiff, orientation);
        }
    }

    private void buildPlan(int hDiff, int vDiff, Orientation orientation) {
        if (hDiff == 0) {
            turnIfnecessaryAndGoStraight(vDiff, orientation, false); // vertical movement
        }
        else if (vDiff == 0) {
            turnIfnecessaryAndGoStraight(hDiff, orientation, true); // horizontal movement
        }
        else {
            buildMixedPlan(hDiff, vDiff, orientation);
        }
    }

    private void buildMixedPlan(int hDiff, int vDiff, Orientation orientation) {
        Orientation referenceHorizontal = getReferenceOrientation(hDiff, true);
        Orientation referrenceVertical = getReferenceOrientation(vDiff, false);

        int numberOfTurnLeftIfHorizontalFirst = orientation.getDifferenceIn90DegreesCCWFrom(referenceHorizontal);
        int numberOfTurnLeftIfVerticalFirst = orientation.getDifferenceIn90DegreesCCWFrom(referrenceVertical);

        if (Math.abs(numberOfTurnLeftIfHorizontalFirst) < Math.abs(numberOfTurnLeftIfVerticalFirst)) {
            horizontallyFirst(hDiff, vDiff, orientation, numberOfTurnLeftIfHorizontalFirst);
        }
        else {
            verticallyFirst(hDiff, vDiff, orientation, numberOfTurnLeftIfVerticalFirst);
        }
    }

    private void verticallyFirst(int hDiff, int vDiff, Orientation orientation, int numberOfTurnLeftIfVerticalFirst) {
        turnAndGoStraight(vDiff, numberOfTurnLeftIfVerticalFirst);
        Orientation newOrientation = applyTurns(orientation, numberOfTurnLeftIfVerticalFirst);
        Orientation reference = getReferenceOrientation(hDiff, true);
        int numberOfTurnLeft = newOrientation.getDifferenceIn90DegreesCCWFrom(reference);
        turnAndGoStraight(hDiff, numberOfTurnLeft);
    }

    private void horizontallyFirst(int hDiff, int vDiff, Orientation orientation, int numberOfTurnLeftIfHorizontalFirst) {
        turnAndGoStraight(hDiff, numberOfTurnLeftIfHorizontalFirst);
        Orientation newOrientation = applyTurns(orientation, numberOfTurnLeftIfHorizontalFirst);
        Orientation reference = getReferenceOrientation(vDiff, false);
        int numberOfTurnLeft = newOrientation.getDifferenceIn90DegreesCCWFrom(reference);
        turnAndGoStraight(vDiff, numberOfTurnLeft);
    }

    private Orientation applyTurns(Orientation orientation, int numberOfTurnLeft) {
        switch (numberOfTurnLeft) {
            case -1:
                return orientation.getRight();
            case 2:
                return orientation.getOpposite();
            case 1:
                return orientation.getLeft();
            case 0:
                return orientation;
            default:
                throw new VacuumWorldRuntimeException();
        }
    }

    private void turnIfnecessaryAndGoStraight(int diff, Orientation orientation, boolean horizontal) {
        Orientation reference = getReferenceOrientation(diff, horizontal);
        int numberOfTurnLeft = orientation.getDifferenceIn90DegreesCCWFrom(reference);
        turnAndGoStraight(diff, numberOfTurnLeft);
    }

    private Orientation getReferenceOrientation(int diff, boolean horizontal) {
        if (horizontal && diff > 0) {
            return Orientation.EAST;
        }
        else if (horizontal && diff < 0) {
            return Orientation.WEST;
        }
        else if (!horizontal && diff > 0) {
            return Orientation.SOUTH;
        }
        else {
            return Orientation.NORTH;
        }
    }

    private void turnAndGoStraight(int diff, int numberOfTurnLeft) {
        switch (numberOfTurnLeft) {
            case -1:
                this.plan.enqueueAction(new VacuumWorldTurnRightAction());
                break;
            case 2:
                this.plan.enqueueAction(new VacuumWorldTurnLeftAction());
                // and fall through.
            case 1:
                this.plan.enqueueAction(new VacuumWorldTurnLeftAction());
                break;
            case 0:
            default:
                break;
        }

        addMoveActions(Math.abs(diff));
    }

    private void addMoveActions(int diff) {
        for (int i = 0; i < diff; i++) {
            this.plan.enqueueAction(new VacuumWorldMoveAction());
        }
    }

    public void rebuildPlan(VacuumWorldCoordinates current, Orientation orientation) {
        buildPlan(current, orientation);
    }

    @Override
    public GoToPositionPlan getPlan() {
        return this.plan;
    }

    @Override
    public boolean isCompleted(Object... current) {
        if (current.length == 0) {
            return false;
        } else {
            return checkCompletion(current[0]);
        }
    }

    private boolean checkCompletion(Object object) {
        if (!VacuumWorldCoordinates.class.isAssignableFrom(object.getClass())) {
            return false;
        }

        return this.target.equals(object);
    }

    @Override
    public boolean hasAtLeastOnePlan() {
        return this.plan != null;
    }
}
