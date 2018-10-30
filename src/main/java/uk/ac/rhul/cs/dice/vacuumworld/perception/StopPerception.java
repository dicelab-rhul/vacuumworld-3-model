package uk.ac.rhul.cs.dice.vacuumworld.perception;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;

public class StopPerception implements Perception {
    private static final long serialVersionUID = -8310587123628439236L;
    private String stopMessage;
    
    public StopPerception() {
	this.stopMessage = "STOP!";
    }
    
    public String getStopMessage() {
	return this.stopMessage;
    }
}