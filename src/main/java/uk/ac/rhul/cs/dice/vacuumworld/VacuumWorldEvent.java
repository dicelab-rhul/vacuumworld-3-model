package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.Serializable;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractActionInterface;

public class VacuumWorldEvent implements VacuumWorldEventInterface, Serializable {
    private static final long serialVersionUID = 4727300589116997781L;
    private VacuumWorldAbstractActionInterface action;
    private long timestamp;
    
    public VacuumWorldEvent(VacuumWorldAbstractActionInterface action) {
	this.action = action;
	this.timestamp = System.nanoTime();
    }

    @Override
    public VacuumWorldAbstractActionInterface getAction() {
        return this.action;
    }
    
    @Override
    public long getTimestamp() {
	return this.timestamp;
    }
}