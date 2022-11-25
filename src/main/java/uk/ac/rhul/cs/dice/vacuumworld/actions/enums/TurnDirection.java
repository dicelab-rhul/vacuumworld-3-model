package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum TurnDirection {
    LEFT, RIGHT;

    public static TurnDirection fromActionType(VacuumWorldPhysicalActionsEnum type) {
        switch(type) {
        case TURN_LEFT:
            return TurnDirection.LEFT;
        case TURN_RIGHT:
            return TurnDirection.RIGHT;
        default:
            throw new IllegalArgumentException();
        }
    }
}
