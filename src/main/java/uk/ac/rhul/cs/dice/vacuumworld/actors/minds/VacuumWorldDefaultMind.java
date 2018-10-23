package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;

public class VacuumWorldDefaultMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 8611021479667623569L;
    private int cycle;
    
    public VacuumWorldDefaultMind(String bodyId) {
	super(bodyId);
	
	this.cycle = 0;
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	//return super.decideRandomly();
	this.cycle++;
	
	if(!getMessages().isEmpty()) {
	    System.out.println("Hello! Cycle #" + this.cycle + ". I am " + getBodyId() + ", and I received messages: " + getMessages().size());
	}
	
	return new VacuumWorldBroadcastingAction(new VacuumWorldMessage(String.valueOf(this.cycle)));
	
    }
    
    @Override
    public void revise() {
	//This does nothing.
    }
}