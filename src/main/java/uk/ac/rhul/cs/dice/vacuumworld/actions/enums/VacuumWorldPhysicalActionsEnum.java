package uk.ac.rhul.cs.dice.vacuumworld.actions.enums;

public enum VacuumWorldPhysicalActionsEnum {
    MOVE('M'), TURN_LEFT('L'), TURN_RIGHT('R'), CLEAN('C'), DROP_DIRT('D');
    
    private char code;
    
    private VacuumWorldPhysicalActionsEnum(char code) {
	this.code = code;
    }
    
    public char getCode() {
	return this.code;
    }
    
    public VacuumWorldPhysicalActionsEnum valueOf(char c) {
	return valueOf(String.valueOf(c));
    }
}