package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum VacuumWorldCommunicativeActionsEnum {
    SPEAK('T'), BROADCAST('B');
    
    private char code;
    
    private VacuumWorldCommunicativeActionsEnum(char code) {
	this.code = code;
    }
    
    public char getCode() {
	return this.code;
    }
    
    public VacuumWorldCommunicativeActionsEnum valueOf(char c) {
	return valueOf(String.valueOf(c));
    }
}