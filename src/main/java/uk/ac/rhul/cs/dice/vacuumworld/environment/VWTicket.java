package uk.ac.rhul.cs.dice.vacuumworld.environment;

public class VWTicket {
    private VWTicketEnum ticketValue;
    
    public VWTicket(VWTicketEnum ticketValue) {
	this.ticketValue = ticketValue;
    }
    
    public VWTicketEnum getTicketValue() {
	return this.ticketValue;
    }
}