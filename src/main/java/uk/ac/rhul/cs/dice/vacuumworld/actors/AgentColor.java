package uk.ac.rhul.cs.dice.vacuumworld.actors;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;

public enum AgentColor {
    GREEN, ORANGE, WHITE, UNDEFINED;
    
    public boolean canClean(VacuumWorldDirtColor color) {
	if(color == null) {
	    throw new IllegalArgumentException();
	}
	else {
	    return color.canBeCleanedBy(this);
	}
    }
}