package uk.ac.rhul.cs.dice.vacuumworld.actions.results;

import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.agentactions.abstractimpl.AbstractActionResult;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;

public class VacuumWorldCommunicativeActionResult extends AbstractActionResult {
    private VacuumWorldMessage message;
    private String senderID;
    private Set<String> recipients;
    
    public VacuumWorldCommunicativeActionResult(ActionResult type, VacuumWorldMessage message, String senderID, Set<String> recipients) {
	super(type);
	
	this.message = message;
	this.senderID = senderID;
	this.recipients = recipients == null ? new HashSet<>() : recipients;
    }
    
    /**
     * 
     * Returns the wrapped {@link VacuumWorldMessage}.
     * 
     * @return the wrapped {@link VacuumWorldMessage}.
     * 
     */
    public VacuumWorldMessage getMessage() {
	return this.message;
    }
    
    /**
     * 
     * Returns the ID of the sender of the wrapped {@link VacuumWorldMessage}.
     * 
     * @return the {@link String} ID of the sender of the wrapped {@link VacuumWorldMessage}.
     * 
     */
    public String getSenderID() {
	return this.senderID;
    }
    
    /**
     * 
     * Returns a {@link Set} of {@link String} IDs of all the recipients of the related communication.
     * 
     * @return a {@link Set} of {@link String} IDs of all the recipients of the related communication.
     * 
     */
    public Set<String> getRecipients() {
	return this.recipients;
    }
}