package uk.ac.rhul.cs.dice.vacuumworld.environment;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;

public class VacuumWorldLocationAppearance implements Appearance {
    private static final long serialVersionUID = -2552147509566518640L;
    private static final int MAX_WALLS = 4;
    private VacuumWorldActorAppearance actorAppearance;
    private VacuumWorldDirtAppearance dirtAppearance;
    private VacuumWorldCoordinates coordinates;
    private boolean wallOnNorth;
    private boolean wallOnSouth;
    private boolean wallOnWest;
    private boolean wallOnEast;
    
    public VacuumWorldLocationAppearance(VacuumWorldCoordinates coordinates, VacuumWorldActorAppearance actorAppearance, VacuumWorldDirtAppearance dirtAppearance, boolean... walls) {
	this.actorAppearance = actorAppearance;
	this.dirtAppearance = dirtAppearance;
	this.coordinates = coordinates;
	
	initWalls(walls);
    }
    
    private void initWalls(boolean[] walls) {
	if(walls.length != MAX_WALLS) {
	    throw new IllegalArgumentException("Expected 4 pieces of information about walls (N, S, W, E), got " + walls.length + ".");
	}
	
	this.wallOnNorth = walls[0];
	this.wallOnSouth = walls[1];
	this.wallOnWest = walls[2];
	this.wallOnEast = walls[3];
    }

    public VacuumWorldActorAppearance getActorAppearance() {
	return this.actorAppearance;
    }
    
    public VacuumWorldDirtAppearance getDirtAppearance() {
	return this.dirtAppearance;
    }
    
    public VacuumWorldCoordinates getCoordinates() {
	return this.coordinates;
    }
    
    public boolean isWallOnNorth() {
	return this.wallOnNorth;
    }
    
    public boolean isWallOnSouth() {
	return this.wallOnSouth;
    }
    
    public boolean isWallOnWest() {
	return this.wallOnWest;
    }
    
    public boolean isWallOnEast() {
	return this.wallOnEast;
    }
}