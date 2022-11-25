package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum VacuumWorldPhysicalActionsEnum {
    MOVE("M"), TURN_LEFT("L"), TURN_RIGHT("R"), CLEAN("C"), DROP_DIRT("D");

    private String code;

    private VacuumWorldPhysicalActionsEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
