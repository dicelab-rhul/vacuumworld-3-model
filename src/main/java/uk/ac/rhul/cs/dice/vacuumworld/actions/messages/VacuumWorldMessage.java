package uk.ac.rhul.cs.dice.vacuumworld.actions.messages;

/**
 * 
 * Implementation of {@link VacuumWorldMessageInterface}.<br /><br />
 * A {@link StringBuilder} is used internally to maintain a representation of the current text for efficiency.
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldMessage implements VacuumWorldMessageInterface {
    private static final long serialVersionUID = -8042200960462345055L;
    private StringBuilder builder;

    /**
     * 
     * Constructs a new {@link VacuumWorldMessage} with an empty {@link String} as text.
     * 
     */
    public VacuumWorldMessage() {
	this("");
    }

    /**
     * 
     * Constructs a new {@link VacuumWorldMessage} with an <code>text</code> as text.
     * 
     * @param text the text of the message.
     * 
     */
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
    
    /**
     * 
     * Concatenates the {@link String} "Message: " with the text of the message, and returns the result.
     * 
     * @return the result of the concatenation of "Message: " and the text of the message.
     * 
     */
    @Override
    public String toString() {
	return "Message: " + getText();
    }
}