package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldLocationPerceptionInterface;

public class VacuumWorldLocationAppearance implements Appearance, VacuumWorldLocationPerceptionInterface {
    private static final long serialVersionUID = -2552147509566518640L;
    private static final int MAX_WALLS = 4;
    private static final String TEXTUAL_BORDER = "X\n";
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

    @Override
    public Appearance getActiveBodyAppearanceIfAny() {
	return this.activeBodyAppearance;
    }
    
    @Override
    public VacuumWorldDirtAppearance getDirtAppearanceIfAny() {
	return this.dirtAppearance;
    }
    
    @Override
    public VacuumWorldCoordinates getCoordinates() {
	return this.coordinates;
    }
    
    @Override
    public boolean isWallOnNorth() {
	return this.wallOnNorth;
    }
    
    @Override
    public boolean isWallOnSouth() {
	return this.wallOnSouth;
    }
    
    @Override
    public boolean isWallOnWest() {
	return this.wallOnWest;
    }
    
    @Override
    public boolean isWallOnEast() {
	return this.wallOnEast;
    }
    
    @Override
    public boolean isWallInFront(String actorId) {
	checkForActiveBody(actorId, "\"In front of ");
	
	return checkForWall(getActiveBodyOrientation());
    }

    @Override
    public boolean isWallOnTheLeft(String actorId) {
	checkForActiveBody(actorId, "\"On the left of ");
	
	return checkForWall(getActiveBodyOrientation().getLeft());
    }
    
    @Override
    public boolean isWallOnTheRight(String actorId) {
	checkForActiveBody(actorId, "\"On the right of ");
	
	return checkForWall(getActiveBodyOrientation().getRight());
    }
    
    @Override
    public boolean isWallBehind(String actorId) {
	checkForActiveBody(actorId, "\"Behind ");
	
	return checkForWall(getActiveBodyOrientation().getOpposite());
    }
    
    private void checkForActiveBody(String actorId, String message) {
	if(!containsSuchActiveBody(actorId)) {
	    throw new VacuumWorldRuntimeException(message + actorId + "\" does not make sense, because there is no such active body on this location.");
	}
    }
    
    @Override
    public boolean checkForWall(Orientation orientation) {
	switch(orientation) {
	case NORTH:
	    return isWallOnNorth();
	case SOUTH:
	    return isWallOnSouth();
	case WEST:
	    return isWallOnWest();
	case EAST:
	    return isWallOnEast();
	default:
	    throw new IllegalArgumentException();
	}
    }
    
    @Override
    public Orientation getActiveBodyOrientation() {
	Orientation orientation = getActiveBodyOrientationIfAny();
	
	if(orientation != null) {
	    return orientation;
	}
	else {
	    throw new IllegalArgumentException();
	}
    }
    
    @Override
    public Orientation getActiveBodyOrientationIfAny() {
	if(isACleaningAgentThere() || isAUserThere()) {
	    return ((VacuumWorldActorAppearance) getActiveBodyAppearanceIfAny()).getOrientation();
	}
	else if(isAnAvatarThere()) {
	    return ((VacuumWorldAvatarAppearance) getActiveBodyAppearanceIfAny()).getOrientation();
	}
	else {
	    return null;
	}
    }
    
    @Override
    public boolean isACleaningAgentThere() {
	return this.activeBodyAppearance != null && VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isCleaningAgent();
    }
    
    @Override
    public boolean isAGreenAgentThere() {
	return this.activeBodyAppearance != null && VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isGreenAgent();
    }
    
    @Override
    public boolean isAnOrangeAgentThere() {
	return this.activeBodyAppearance != null && VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isOrangeAgent();
    }
    
    @Override
    public boolean isAWhiteAgentThere() {
	return this.activeBodyAppearance != null && VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isWhiteAgent();
    }
    
    @Override
    public boolean isACleaningAgentWithSuchColorThere(AgentColor color) {
	return isACleaningAgentThere() && color.equals(((VacuumWorldActorAppearance) this.activeBodyAppearance).getColor());
    }
    
    @Override
    public boolean isACleaningAgentCompatibleWithSuchDirtThere(VacuumWorldDirtColor color) {
	return isACleaningAgentThere() && color.canBeCleanedBy(((VacuumWorldActorAppearance) this.activeBodyAppearance).getColor());
    }
    
    @Override
    public boolean isAUserThere() {
	return this.activeBodyAppearance != null && VacuumWorldActorAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass()) && ((VacuumWorldActorAppearance) this.activeBodyAppearance).isUser();
    }
    
    @Override
    public boolean isAnAvatarThere() {
	return this.activeBodyAppearance != null && VacuumWorldAvatarAppearance.class.isAssignableFrom(this.activeBodyAppearance.getClass());
    }
    
    @Override
    public boolean isAnActiveBodyThere() {
	return this.activeBodyAppearance != null;
    }
    
    @Override
    public boolean isFreeFromActiveBodies() {
	return this.activeBodyAppearance == null;
    }
    
    @Override
    public boolean isDirtThere() {
	return this.dirtAppearance != null;
    }
    
    @Override
    public boolean isSuchDirtThere(VacuumWorldDirtColor color) {
	return this.dirtAppearance != null && color != null && color.equals(this.dirtAppearance.getColor());
    }
    
    @Override
    public boolean isCompatibleDirtThere(AgentColor color) {
	return this.dirtAppearance != null && color != null && color.canClean(this.dirtAppearance.getColor());
    }
    
    @Override
    public boolean isGreenDirtThere() {
	return this.dirtAppearance != null && VacuumWorldDirtColor.GREEN.equals(this.dirtAppearance.getColor());
    }
    
    @Override
    public boolean isOrangeDirtThere() {
	return this.dirtAppearance != null && VacuumWorldDirtColor.ORANGE.equals(this.dirtAppearance.getColor());
    }
    
    @Override
    public boolean isEmpty() {
	return this.activeBodyAppearance == null && this.dirtAppearance == null;
    }
    
    @Override
    public boolean containsSuchActiveBody(String id) {
	return containsSuchCleaningAgent(id) || containsSuchUser(id) || containsSuchAvatar(id);
    }
    
    @Override
    public boolean containsSuchCleaningAgent(String id) {
	return isACleaningAgentThere() && id.equals(this.getAgentAppearanceIfAny().getId());
    }
    
    @Override
    public boolean containsSuchUser(String id) {
	return isAUserThere() && id.equals(this.getUserAppearanceIfAny().getId());
    }
    
    @Override
    public boolean containsSuchAvatar(String id) {
	return isAnAvatarThere() && id.equals(this.getAvatarAppearanceIfAny().getId());
    }
    
    @Override
    public VacuumWorldActorAppearance getAgentAppearanceIfAny() {
	return isACleaningAgentThere() ? (VacuumWorldActorAppearance) this.activeBodyAppearance : null;
    }
    
    @Override
    public VacuumWorldActorAppearance getUserAppearanceIfAny() {
	return isAUserThere() ? (VacuumWorldActorAppearance) this.activeBodyAppearance : null;
    }
    
    @Override
    public VacuumWorldAvatarAppearance getAvatarAppearanceIfAny() {
	return isAnAvatarThere() ? (VacuumWorldAvatarAppearance) this.activeBodyAppearance : null;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append("#########\n");
        builder.append("#       #\n");
        builder.append(getMiddleLine());
        builder.append("#       #\n");
        builder.append("#########\n");
        
        return builder.toString();
    }

    private Object getMiddleLine() {
	StringBuilder builder = new StringBuilder();
	
	builder.append("#");
	
	if(isACleaningAgentThere()) {
	    builder.append(getMiddleLineWithAgent());
	}
	else if(isAUserThere()) {
	    builder.append(getMiddleLineWithUser());
	}
	else if(isAnAvatarThere()) {
	    builder.append(getMiddleLineWithAvatar());
	}
	else if(isDirtThere()) {
	    builder.append(getMiddleLineWithDirt());
	}
	else {
	    builder.append("       ");
	    builder.append(TEXTUAL_BORDER);
	}
	
	return builder.toString();
    }

    private String getMiddleLineWithDirt() {
	return "   " + this.dirtAppearance.getColor().toChar() + "   " + TEXTUAL_BORDER;
    }

    private String getMiddleLineWithAvatar() {
	if(isDirtThere()) {
	    return "  " + ActorType.AVATAR.toChar() + "+" + this.dirtAppearance.getColor().toChar() + "  " + TEXTUAL_BORDER;
	}
	else {
	    return "   " + ActorType.AVATAR.toChar() + "   "  + TEXTUAL_BORDER;
	}
    }

    private String getMiddleLineWithUser() {
	if(isDirtThere()) {
	    return "  " + ActorType.USER.toChar() + "+" + this.dirtAppearance.getColor().toChar() + "  "  + TEXTUAL_BORDER;
	}
	else {
	    return "   " + ActorType.USER.toChar() + "   " + TEXTUAL_BORDER;
	}
    }

    private String getMiddleLineWithAgent() {
	if(isDirtThere()) {
	    return "  " + ((VacuumWorldActorAppearance) this.activeBodyAppearance).getColor().toChar() + "+" + this.dirtAppearance.getColor().toChar() + "  "  + TEXTUAL_BORDER;
	}
	else {
	    return "   " + ((VacuumWorldActorAppearance) this.activeBodyAppearance).getColor().toChar() + "   "  + TEXTUAL_BORDER;
	}
    }
}