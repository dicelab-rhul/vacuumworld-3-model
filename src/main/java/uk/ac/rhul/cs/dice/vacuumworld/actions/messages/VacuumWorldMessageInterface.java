package uk.ac.rhul.cs.dice.vacuumworld.actions.messages;

import java.io.Serializable;

public interface VacuumWorldMessageInterface extends Serializable {
    public abstract String getText();
    public abstract void setText(CharSequence text);
    public abstract void appendAsIs(CharSequence toAppend);
    public abstract void appendWithSpaceBefore(CharSequence toAppend);
    public abstract void appendWithNewLineBefore(CharSequence toAppend);
}