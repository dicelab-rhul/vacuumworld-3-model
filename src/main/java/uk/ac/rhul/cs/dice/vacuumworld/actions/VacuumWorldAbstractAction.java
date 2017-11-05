package uk.ac.rhul.cs.dice.vacuumworld.actions;

import java.io.Serializable;

public abstract class VacuumWorldAbstractAction implements VacuumWorldAbstractActionInterface, Serializable {
    private static final long serialVersionUID = -600283328134615689L;
    private String actor;
    
    public VacuumWorldAbstractAction() {}
    
    public VacuumWorldAbstractAction(String actor) {
	this.actor = actor;
    }
    
    @Override
    public String getActorID() {
	return this.actor;
    }
    
    @Override
    public void setActor(String actor) {
	this.actor = actor;
    }
}