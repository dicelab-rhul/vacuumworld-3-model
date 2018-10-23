package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldBroadcastingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

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
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	getMessages().forEach(this::printMessageDetails);
	
	VacuumWorldMessage message = new VacuumWorldMessage("Hello everyone! I am " + getBodyId() + ". I am sending this on cycle " + this.cycle + ", and you should receive this on cycle " + (this.cycle + 1) + ".");
	
	return new VacuumWorldBroadcastingAction(message);
    }
    
    private void printMessageDetails(VacuumWorldSpeechPerception received) {
	LogUtils.log(getBodyId() + ": cycle " + this.cycle + ". Received this:\n  " + received.getMessage().getText());
    }
}