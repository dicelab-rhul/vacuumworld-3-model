package uk.ac.rhul.cs.dice.vacuumworld.perception;

import java.io.Serializable;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.interfaces.ActiveBody;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAvatarAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

/**
 * 
 * This in an interface for the perception API for a single {@link VacuumWorldLocation}.<br /><br />
 * 
 * Known implementations: {@link VacuumWorldLocationAppearance}.
 * 
 * @author cloudstrife9999
 *
 */
public interface VacuumWorldLocationPerceptionInterface extends Serializable {
    
    public abstract JsonObject serialize();
    
    /**
     * 
     * Returns the {@link Appearance} of the {@link ActiveBody} that is present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     * @return the {@link Appearance} of the {@link ActiveBody} that is present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract Appearance getActiveBodyAppearanceIfAny();
    
    /**
     * 
     * Returns the {@link VacuumWorldDirtAppearance} of the {@link VacuumWorldDirt} that is present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     * @return the {@link VacuumWorldDirtAppearance} of the {@link VacuumWorldDirt} that is present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract VacuumWorldDirtAppearance getDirtAppearanceIfAny();
    
    /**
     * 
     * Returns the {@link VacuumWorldCoordinates} of this {@link VacuumWorldLocation}.
     * 
     * @return the {@link VacuumWorldCoordinates} of this {@link VacuumWorldLocation}.
     * 
     */
    public abstract VacuumWorldCoordinates getCoordinates();
    
    /**
     * 
     * Checks whether there is a wall on the northern side of this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not there is a wall on the northern side of this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isWallOnNorth();
    
    /**
     * 
     * Checks whether there is a wall on the southern side of this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not there is a wall on the southern side of this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isWallOnSouth();
    
    /**
     * 
     * Checks whether there is a wall on the western side of this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not there is a wall on the western side of this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isWallOnWest();
    
    /**
     * 
     * Checks whether there is a wall on the eastern side of this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not there is a wall on the eastern side of this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isWallOnEast();
    
    /**
     * 
     * Checks whether there is an {@link ActiveBody} with <code>actorId</code> as ID and returns whether, according to its {@link Orientation}, there is a wall in front.<br />
     * If no {@link ActiveBody} with <code>actorId</code> as ID is found, a subclass of {@link RuntimeException} must be raised.
     * 
     * @param actorId the {@link String} ID to check an {@link ActiveBody} presence against.
     * 
     * @return whether, according to the {@link Orientation} of the {@link ActiveBody} whose ID is <code>actorId</code>, there is a wall in front.
     * 
     */
    public abstract boolean isWallInFront(String actorId);

    /**
     * 
     * Checks whether there is an {@link ActiveBody} with <code>actorId</code> as ID and returns whether, according to its {@link Orientation}, there is a wall on the left.<br />
     * If no {@link ActiveBody} with <code>actorId</code> as ID is found, a subclass of {@link RuntimeException} must be raised.
     * 
     * @param actorId the {@link String} ID to check an {@link ActiveBody} presence against.
     * 
     * @return whether, according to the {@link Orientation} of the {@link ActiveBody} whose ID is <code>actorId</code>, there is a wall on the left.
     * 
     */
    public abstract boolean isWallOnTheLeft(String actorId);
    
    /**
     * 
     * Checks whether there is an {@link ActiveBody} with <code>actorId</code> as ID and returns whether, according to its {@link Orientation}, there is a wall on the right.<br />
     * If no {@link ActiveBody} with <code>actorId</code> as ID is found, a subclass of {@link RuntimeException} must be raised.
     * 
     * @param actorId the {@link String} ID to check an {@link ActiveBody} presence against.
     * 
     * @return whether, according to the {@link Orientation} of the {@link ActiveBody} whose ID is <code>actorId</code>, there is a wall on the right.
     * 
     */
    public abstract boolean isWallOnTheRight(String actorId);
    
    /**
     * 
     * Checks whether there is an {@link ActiveBody} with <code>actorId</code> as ID and returns whether, according to its {@link Orientation}, there is a wall behind.<br />
     * If no {@link ActiveBody} with <code>actorId</code> as ID is found, a subclass of {@link RuntimeException} must be raised.
     * 
     * @param actorId the {@link String} ID to check an {@link ActiveBody} presence against.
     * 
     * @return whether, according to the {@link Orientation} of the {@link ActiveBody} whose ID is <code>actorId</code>, there is a wall behind.
     * 
     */
    public abstract boolean isWallBehind(String actorId);
    
    /**
     * 
     * Checks of the presence of a wall on the side specified by <code>orientation</code>.
     * 
     * @param orientation an {@link Orientation} instance, specifying the side which needs to be checked.
     * 
     * @return whether or not there is a wall on the side specified by <code>orientation</code>.
     * 
     */
    public abstract boolean checkForWall(Orientation orientation);
    
    /**
     * 
     * Returns the {@link Orientation} of the {@link ActiveBody} present on this {@link VacuumWorldLocation}.<br />
     * If no {@link ActiveBody} is found, a subclass of {@link RuntimeException} must be raised.
     * 
     * @return the {@link Orientation} of the {@link ActiveBody} present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract Orientation getActiveBodyOrientation();
    
    /**
     * 
     * Returns the {@link Orientation} of the {@link ActiveBody} present on this {@link VacuumWorldLocation}, if any.
     * 
     * @return the {@link Orientation} of the {@link ActiveBody} present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract Orientation getActiveBodyOrientationIfAny();
    
    /**
     * 
     * Checks whether a {@link VacuumWorldCleaningAgent} is present of this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldCleaningAgent} is present of this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isACleaningAgentThere();
    
    public abstract boolean isAGreenAgentThere();
    
    public abstract boolean isAnOrangeAgentThere();
    
    public abstract boolean isAWhiteAgentThere();
    
    public abstract boolean isACleaningAgentWithSuchColorThere(AgentColor color);
    
    public abstract boolean isACleaningAgentCompatibleWithSuchDirtThere(VacuumWorldDirtColor color);
    
    public abstract boolean isAUserThere();
    
    public abstract boolean isAnAvatarThere();
    
    public abstract boolean isAnActiveBodyThere();
    
    public abstract boolean isFreeFromActiveBodies();
    
    public abstract boolean isDirtThere();
    
    public abstract boolean isSuchDirtThere(VacuumWorldDirtColor color);
    
    public abstract boolean isCompatibleDirtThere(AgentColor color);
    
    public abstract boolean isGreenDirtThere() ;
    
    public abstract boolean isOrangeDirtThere();
    
    public abstract boolean isEmpty();
    
    public abstract boolean containsSuchActiveBody(String id);
    
    public abstract boolean containsSuchCleaningAgent(String id);
    
    public abstract boolean containsSuchUser(String id);
    
    public abstract boolean containsSuchAvatar(String id);
    
    public abstract VacuumWorldActorAppearance getAgentAppearanceIfAny();
    
    public abstract VacuumWorldActorAppearance getUserAppearanceIfAny();
    
    public abstract VacuumWorldAvatarAppearance getAvatarAppearanceIfAny();
}