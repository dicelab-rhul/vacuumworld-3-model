package uk.ac.rhul.cs.dice.vacuumworld.actions.messages;

import java.io.Serializable;

@FunctionalInterface
public interface VacuumWorldMessageInterface extends Serializable {
    public abstract String getText();
}