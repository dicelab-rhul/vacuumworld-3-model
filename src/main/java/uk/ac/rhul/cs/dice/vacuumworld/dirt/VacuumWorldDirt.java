package uk.ac.rhul.cs.dice.vacuumworld.dirt;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;

public class VacuumWorldDirt implements VacuumWorldDirtInterface {
    private VacuumWorldDirtAppearance appearance;
    
    public VacuumWorldDirt(VacuumWorldDirtColor color) {
	if(color == null) {
	    throw new IllegalArgumentException();
	}
	else {
	    this.appearance = new VacuumWorldDirtAppearance(color);
	}
    }
    
    public VacuumWorldDirt(VacuumWorldDirt toCopy) {
	this(toCopy.getAppearance().getColor());
    }

    @Override
    public VacuumWorldDirtAppearance getAppearance() {
	return this.appearance;
    }
}