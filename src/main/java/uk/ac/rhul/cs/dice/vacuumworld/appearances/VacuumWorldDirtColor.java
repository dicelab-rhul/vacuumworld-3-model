package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;

public enum VacuumWorldDirtColor {
    GREEN, ORANGE;
    
    public boolean canBeCleanedBy(AgentColor agentColor) {
	switch(this) {
	case GREEN:
	    return AgentColor.GREEN.equals(agentColor) || AgentColor.WHITE.equals(agentColor);
	case ORANGE:
	    return AgentColor.ORANGE.equals(agentColor) || AgentColor.WHITE.equals(agentColor);
	default:
	    throw new UnsupportedOperationException();
	}
    }
}