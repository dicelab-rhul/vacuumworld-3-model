package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;

public class VacuumWorldShoutingMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 572122589076420561L;
    private int cycle;

    public VacuumWorldShoutingMind(String bodyId) {
	super(bodyId);
	
	this.cycle = 0;
    }

    @Override
    public void revise() {
	this.cycle++;
	StringBuilder builder = new StringBuilder(getBodyId() + ": cycle " + this.cycle  + ". ");
	
	if(hasNewMessages()) {
	    builder.append("Received these:");
	    getMessages().forEach(m -> builder.append("\n  " + m.getMessage().getText()));
	}
	else {
	    builder.append("\n  No new messages available.");
	}
	
	LogUtils.log(builder.toString());
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	VacuumWorldMessage message = new VacuumWorldMessage("Hello everyone! I am " + getBodyId() + ". I am sending this on cycle " + this.cycle + ", and you should receive this on cycle " + (this.cycle + 1) + ".");
	
	return new VacuumWorldBroadcastingAction(message, getBodyId());
    }
}