package uk.ac.rhul.cs.dice.vacuumworld.perception;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldSpeechPerception implements Perception {
    private static final long serialVersionUID = 9175964739556380663L;
    private VacuumWorldMessage message;
    private VacuumWorldActorAppearance sender;
    
    public VacuumWorldSpeechPerception(VacuumWorldMessage message, VacuumWorldActorAppearance sender) {
	this.message = message;
	this.sender = sender;
    }
    
    public VacuumWorldMessage getMessage() {
	return this.message;
    }
    
    public VacuumWorldActorAppearance getSender() {
	return this.sender;
    }
}