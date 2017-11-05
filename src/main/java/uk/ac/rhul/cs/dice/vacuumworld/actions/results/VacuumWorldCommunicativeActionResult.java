package uk.ac.rhul.cs.dice.vacuumworld.actions.results;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.agentactions.abstractimpl.AbstractActionResult;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;

public class VacuumWorldCommunicativeActionResult extends AbstractActionResult {
    private VacuumWorldMessage message;
    private Set<String> recipients;
    
    public VacuumWorldCommunicativeActionResult(ActionResult type, VacuumWorldMessage message, Set<String> recipients) {
	super(type);
	
	this.message = message;
	this.recipients = recipients == null ? new HashSet<>() : recipients;
    }
    
    public VacuumWorldMessage getMessage() {
	return this.message;
    }
    
    public Set<String> getRecipients() {
	return this.recipients;
    }
}