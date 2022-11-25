package uk.ac.rhul.cs.dice.vacuumworld.perception;

import java.io.Serializable;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.interfaces.ActiveBody;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAutonomousActorAppearance;
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
     * Returns the {@link VacuumWorldActorAppearance} of the {@link ActiveBody} that is present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     * @return the {@link VacuumWorldActorAppearance} of the {@link ActiveBody} that is present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract VacuumWorldActorAppearance getActiveBodyAppearanceIfAny();

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
    public default Orientation getActiveBodyOrientation() {
        Orientation orientation = getActiveBodyOrientationIfAny();

        if(orientation != null) {
            return orientation;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 
     * Returns the {@link Orientation} of the {@link ActiveBody} present on this {@link VacuumWorldLocation}, if any.
     * 
     * @return the {@link Orientation} of the {@link ActiveBody} present on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public default Orientation getActiveBodyOrientationIfAny() {
        return isAnActiveBodyThere() ? getActiveBodyAppearanceIfAny().getOrientation() : null;
    }

    /**
     * 
     * Checks whether a {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isACleaningAgentThere();

    /**
     * 
     * Checks whether a <code>GREEN</code> {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a <code>GREEN</code> {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isAGreenAgentThere();

    /**
     * 
     * Checks whether an <code>ORANGE</code> {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not an <code>ORANGE</code> {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isAnOrangeAgentThere();

    /**
     * 
     * Checks whether a <code>WHITE</code> {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a <code>WHITE</code> {@link VacuumWorldCleaningAgent} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isAWhiteAgentThere();

    /**
     * 
     * Checks whether a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} matches <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param color the {@link AgentColor} of the {@link VacuumWorldCleaningAgent} this method is looking for.
     * 
     * @return whether or not a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} matches <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isACleaningAgentWithSuchColorThere(AgentColor color);

    /**
     * 
     * Checks whether a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is compatible with <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param color the {@link VacuumWorldDirtColor} which needs to be compatible with the {@link AgentColor} of the {@link VacuumWorldCleaningAgent} this method is looking for.
     * 
     * @return whether or not a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is compatible with <code>color</code> is present on this {@link VacuumWorldLocation}..
     * 
     */
    public abstract boolean isACleaningAgentCompatibleWithSuchDirtThere(VacuumWorldDirtColor color);

    /**
     * 
     * Checks whether a {@link VacuumWorldUserAgent} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldUserAgent} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isAUserThere();

    /**
     * 
     * Checks whether a {@link VacuumWorldAvatar} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldAvatar} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isAnAvatarThere();

    /**
     * 
     * Checks whether an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isAnActiveBodyThere();

    /**
     * 
     * Checks whether no instance of an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) is present on this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not no instance of an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isFreeFromActiveBodies();

    /**
     * 
     * Checks whether a {@link VacuumWorldDirt} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldDirt} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isDirtThere();

    /**
     * 
     * Checks whether a {@link VacuumWorldDirt} is not present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldDirt} is not present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isFreeFromDirt();

    /**
     * 
     * Checks whether a {@link VacuumWorldDirt} whose {@link VacuumWorldDirtColor} matches <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param color the {@link VacuumWorldDirtColor} of the {@link VacuumWorldDirt} this method is looking for on this {@link VacuumWorldLocation}..
     * 
     * @return whether or not a {@link VacuumWorldDirt} whose {@link VacuumWorldDirtColor} matches <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isSuchDirtThere(VacuumWorldDirtColor color);

    /**
     * 
     * Checks whether a {@link VacuumWorldDirt} whose {@link VacuumWorldDirtColor} is compatible with <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param color the {@link AgentColor} which needs to be compatible with the {@link VacuumWorldDirtColor} of the {@link VacuumWorldDirt} this method is looking for on this {@link VacuumWorldLocation}..
     * 
     * @return whether or not a {@link VacuumWorldDirt} whose {@link VacuumWorldDirtColor} is compatible with <code>color</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isCompatibleDirtThere(AgentColor color);

    /**
     * 
     * Checks whether a <code>GREEN</code> {@link VacuumWorldDirt} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a <code>GREEN</code> {@link VacuumWorldDirt} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isGreenDirtThere() ;

    /**
     * 
     * Checks whether an <code>ORANGE</code> {@link VacuumWorldDirt} is present on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not an <code>ORANGE</code> {@link VacuumWorldDirt} is present on this {@link VacuumWorldLocation}.
     * 
     */
    public abstract boolean isOrangeDirtThere();

    /**
     * 
     * Checks whether no instance of an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) and no instance of a {@link VacuumWorldDirt} are present on this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not no instance of an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) and no instance of a {@link VacuumWorldDirt} are present on this {@link VacuumWorldLocation}.
     * 
     */
    public default boolean isEmpty() {
        return isFreeFromActiveBodies() && isFreeFromDirt();
    }

    /**
     * 
     * Checks whether an instance of an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) or an instance of a {@link VacuumWorldDirt} are present on this {@link VacuumWorldLocation}.
     * 
     * @return  whether or not an instance of an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) or an instance of a {@link VacuumWorldDirt} are present on this {@link VacuumWorldLocation}.
     * 
     */
    public default boolean isNotEmpty() {
        return isAnActiveBodyThere() || isDirtThere();
    }

    /**
     * 
     * Checks whether an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param id the <code>ID</code> of the {@link ActiveBody} this method is looking for on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not an {@link ActiveBody} ({@link VacuumWorldCleaningAgent}, {@link VacuumWorldUserAgent}, or {@link VacuumWorldAvatar}) whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public default boolean containsSuchActiveBody(String id) {
        return containsSuchCleaningAgent(id) || containsSuchUser(id) || containsSuchAvatar(id);
    }

    /**
     * 
     * Checks whether a {@link VacuumWorldCleaningAgent} whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param id the <code>ID</code> of the {@link VacuumWorldCleaningAgent} this method is looking for on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldCleaningAgent} whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public default boolean containsSuchCleaningAgent(String id) {
        return isACleaningAgentThere() && id.equals(this.getAgentAppearanceIfAny().getId());
    }

    /**
     * 
     * Checks whether a {@link VacuumWorldUserAgent} whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param id the <code>ID</code> of the {@link VacuumWorldUserAgent} this method is looking for on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldUserAgent} whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public default boolean containsSuchUser(String id) {
        return isAUserThere() && id.equals(this.getUserAppearanceIfAny().getId());
    }

    /**
     * 
     * Checks whether a {@link VacuumWorldAvatar} whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     * @param id the <code>ID</code> of the {@link VacuumWorldAvatar} this method is looking for on this {@link VacuumWorldLocation}.
     * 
     * @return whether or not a {@link VacuumWorldAvatar} whose ID matches <code>id</code> is present on this {@link VacuumWorldLocation}.
     * 
     */
    public default boolean containsSuchAvatar(String id) {
        return isAnAvatarThere() && id.equals(this.getAvatarAppearanceIfAny().getId());
    }

    /**
     * 
     * Returns the {@link VacuumWorldAutonomousActorAppearance} of the {@link VacuumWorldCleaningAgent} on this {@link VacuumWorldLocation}, if any.
     * 
     * @return the {@link VacuumWorldAutonomousActorAppearance} of the {@link VacuumWorldCleaningAgent} on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract VacuumWorldAutonomousActorAppearance getAgentAppearanceIfAny();

    /**
     * 
     * Returns the {@link VacuumWorldAutonomousActorAppearance} of the {@link VacuumWorldUserAgent} on this {@link VacuumWorldLocation}, if any.
     * 
     * @return the {@link VacuumWorldAutonomousActorAppearance} of the {@link VacuumWorldUserAgent} on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract VacuumWorldAutonomousActorAppearance getUserAppearanceIfAny();

    /**
     * 
     * Returns the {@link VacuumWorldAvatarAppearance} of the {@link VacuumWorldAvatar} on this {@link VacuumWorldLocation}, if any.
     * 
     * @return the {@link VacuumWorldAvatarAppearance} of the {@link VacuumWorldAvatar} on this {@link VacuumWorldLocation}, if any, <code>null</code> otherwise.
     * 
     */
    public abstract VacuumWorldAvatarAppearance getAvatarAppearanceIfAny();
}
