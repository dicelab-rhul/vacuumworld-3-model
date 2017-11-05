package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.io.Serializable;

public interface VacuumWorldAbstractActionInterface extends Serializable {
    public abstract String getActorID();
    public abstract void setActor(String actor);
}