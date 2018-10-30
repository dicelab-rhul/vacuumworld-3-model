package uk.ac.rhul.cs.dice.vacuumworld.actions.messages;

import java.io.Serializable;

/**
 * 
 * Interface for messages in VacuumWorld.<br /><br />
 * Known subclasses: {@link VacuumWorldMessage}.
 * 
 * @author cloudstrife9999
 *
 */
public interface VacuumWorldMessageInterface extends Serializable {
    
    /**
     * 
     * Returns the text of the message as a {@link String}. The returned value cannot be <code>null</code>. At the very least it consists of an empty {@link String}.
     * 
     * @return the text of the message as a {@link String}.
     * 
     */
    public abstract String getText();
    
    /**
     * 
     * Discards whatever is the current text, and replaces it with <code>text</code>.
     * 
     * @param text the new text.
     * 
     */
    public abstract void setText(CharSequence text);
    
    /**
     * 
     * Appends <code>toAppend</code> to the current text.
     * 
     * @param toAppend the text to append.
     * 
     */
    public abstract void appendAsIs(CharSequence toAppend);
    
    /**
     * 
     * Appends a space to the current text, and then appends <code>toAppend</code>.
     * 
     * @param toAppend the text to append.
     * 
     */
    public abstract void appendWithSpaceBefore(CharSequence toAppend);
    
    /**
     * 
     * Appends a newline ('\n') to the current text, and then appends <code>toAppend</code>.
     * 
     * @param toAppend the text to append.
     * 
     */
    public abstract void appendWithNewLineBefore(CharSequence toAppend);
}