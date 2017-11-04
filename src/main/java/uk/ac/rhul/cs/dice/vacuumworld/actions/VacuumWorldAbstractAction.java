package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;

public abstract class VacuumWorldAbstractAction implements VacuumWorldAbstractActionInterface {
    private Actor actor;
    
    public VacuumWorldAbstractAction() {}
    
    public VacuumWorldAbstractAction(Actor actor) {
	this.actor = actor;
    }
    
    @Override
    public Actor getActor() {
	return this.actor;
    }
    
    @Override
    public void setActor(Actor actor) {
	this.actor = actor;
    }
}