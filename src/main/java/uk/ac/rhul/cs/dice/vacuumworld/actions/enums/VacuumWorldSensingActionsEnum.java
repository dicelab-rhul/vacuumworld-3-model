package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum VacuumWorldSensingActionsEnum {
    STAY_IDLE("I");

    private String code;

    private VacuumWorldSensingActionsEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
