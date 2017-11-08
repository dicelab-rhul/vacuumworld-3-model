package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum VacuumWorldSensingActionsEnum {
    SENSE('S'), STAY_IDLE('I');
    
    private char code;
    
    private VacuumWorldSensingActionsEnum(char code) {
	this.code = code;
    }
    
    public char getCode() {
	return this.code;
    }
    
    public VacuumWorldSensingActionsEnum valueOf(char c) {
	return valueOf(String.valueOf(c));
    }
}