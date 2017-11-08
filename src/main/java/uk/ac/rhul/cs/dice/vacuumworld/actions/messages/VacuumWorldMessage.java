package uk.ac.rhul.cs.dice.vacuumworld.actions.messages;

public class VacuumWorldMessage implements VacuumWorldMessageInterface {
    private static final long serialVersionUID = -8042200960462345055L;
    private String text;
    
    public VacuumWorldMessage() {
	this.text = "";
    }
    
    public VacuumWorldMessage(String text) {
	this.text = text;
    }
    
    @Override
    public String getText() {
	return this.text;
    }
}
