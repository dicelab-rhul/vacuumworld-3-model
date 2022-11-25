package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum VacuumWorldCommunicativeActionsEnum {
    SPEAK("S"), BROADCAST("B");

    private String code;

    private VacuumWorldCommunicativeActionsEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
