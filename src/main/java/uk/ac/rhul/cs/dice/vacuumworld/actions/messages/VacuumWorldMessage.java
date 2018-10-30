package uk.ac.rhul.cs.dice.vacuumworld.actions.messages;

public class VacuumWorldMessage implements VacuumWorldMessageInterface {
    private static final long serialVersionUID = -8042200960462345055L;
    private StringBuilder builder;

    public VacuumWorldMessage() {
	this("");
    }

    public VacuumWorldMessage(CharSequence text) {
	this.builder = new StringBuilder(text);
    }

    @Override
    public String getText() {
	return this.builder.toString();
    }
    
    @Override
    public void setText(CharSequence text) {
	this.builder = new StringBuilder(text);
    }
    
    @Override
    public void appendAsIs(CharSequence toAppend) {
	this.builder.append(toAppend);
    }
    
    @Override
    public void appendWithSpaceBefore(CharSequence toAppend) {
	this.builder.append(' ');
	this.builder.append(toAppend);
    }
    
    @Override
    public void appendWithNewLineBefore(CharSequence toAppend) {
	this.builder.append('\n');
	this.builder.append(toAppend);
    }
    
    @Override
    public String toString() {
	return "Message: " + getText();
    }
}