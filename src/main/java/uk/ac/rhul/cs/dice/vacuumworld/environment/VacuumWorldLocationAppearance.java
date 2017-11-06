package uk.ac.rhul.cs.dice.vacuumworld.environment;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAvatarAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;

public class VacuumWorldLocationAppearance implements Appearance {
    private static final long serialVersionUID = -2552147509566518640L;
    private static final int MAX_WALLS = 4;
    private Appearance activeBodyAppearance;
    private VacuumWorldDirtAppearance dirtAppearance;
    private VacuumWorldCoordinates coordinates;
    private boolean wallOnNorth;
    private boolean wallOnSouth;
    private boolean wallOnWest;
    private boolean wallOnEast;
    
    public VacuumWorldLocationAppearance(VacuumWorldCoordinates coordinates, Appearance activeBodyAppearance, VacuumWorldDirtAppearance dirtAppearance, boolean... walls) {
	this.activeBodyAppearance = activeBodyAppearance;
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

    public Appearance getActiveBodyAppearanceIfAny() {
	return this.activeBodyAppearance;
    }
    
    public VacuumWorldDirtAppearance getDirtAppearanceIfAny() {
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
    
    public boolean isACleaningAgentThere() {
	return VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isCleaningAgent();
    }
    
    public boolean isAGreenAgentThere() {
	return VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isGreenAgent();
    }
    
    public boolean isAnOrangeAgentThere() {
	return VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isOrangeAgent();
    }
    
    public boolean isAWhiteAgentThere() {
	return VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isWhiteAgent();
    }
    
    public boolean isAUserThere() {
	return VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isUser();
    }
    
    public boolean isAnAvatarThere() {
	return VacuumWorldAvatarAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass());
    }
    
    public boolean isDirtThere() {
	return this.dirtAppearance != null;
    }
    
    public boolean isGreenDirtThere() {
	return this.dirtAppearance != null && VacuumWorldDirtColor.GREEN.equals(this.dirtAppearance.getColor());
    }
    
    public boolean isOrangeDirtThere() {
	return this.dirtAppearance != null && VacuumWorldDirtColor.ORANGE.equals(this.dirtAppearance.getColor());
    }
    
    public VacuumWorldActorAppearance getAgentAppearanceIfAny() {
	return isACleaningAgentThere() ? (VacuumWorldActorAppearance) this.activeBodyAppearance : null;
    }
    
    public VacuumWorldActorAppearance getUserAppearanceIfAny() {
	return isAUserThere() ? (VacuumWorldActorAppearance) this.activeBodyAppearance : null;
    }
    
    public VacuumWorldAvatarAppearance getAvatarAppearanceIfAny() {
	return isAnAvatarThere() ? (VacuumWorldAvatarAppearance) this.activeBodyAppearance : null;
    }
}