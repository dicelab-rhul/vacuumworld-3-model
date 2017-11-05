package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;

public class VacuumWorldDirtAppearance implements Appearance {
    private VacuumWorldDirtColor color;
    
    public VacuumWorldDirtAppearance(VacuumWorldDirtColor color) {
	this.color = color;
    }
    
    public VacuumWorldDirtColor getColor() {
	return this.color;
    }
}