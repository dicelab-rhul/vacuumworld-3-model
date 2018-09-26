package uk.ac.rhul.cs.dice.vacuumworld.perception;

import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.agent.interfaces.ActiveBody;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;

/**
 * 
 * This is an interface for the perception API for multiple {@link VacuumWorldLocation}s.<br /><br />
 * 
 * Such multiple locations are arranged in a 3x2 grid, unless some of them don't exist.
 * In that case, the grid can be 2x2, 3x1, 2x1 or 1x2. The {@link ActiveBody} this perception is directed at is always in the back-central position of the 3x2 grid.<br /><br />
 * 
 * Known implementations: {@link VacuumWorldEnvironmentAppearance}.
 * 
 * @author cloudstrife9999
 *
 */
public interface VacuumWorldGridPerceptionInterface {
    
    /**
     * 
     * Returns the perception grid, as an immutable {@link Map} from {@link VacuumWorldCoordinates} to {@link VacuumWorldLocationAppearance}.
     * 
     * @return the perception grid, as an immutable {@link Map} from {@link VacuumWorldCoordinates} to {@link VacuumWorldLocationAppearance}.
     * 
     */
    public abstract Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> getGrid();
    
    /**
     * 
     * Counts the number of {@link VacuumWorldLocation}s that are present in the perception.
     * 
     * @return the number of {@link VacuumWorldLocation}s that are present in the perception.
     * 
     */
    public abstract int countNumberOfLocations();
    
    /**
     * 
     * Returns whether the {@link VacuumWorldLocation} on the left w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * Left is relative to the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} on the {@link VacuumWorldLocation} to use as base location.
     * 
     * @return whether or not the {@link VacuumWorldLocation} on the left w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * 
     */
    public default boolean doesLeftExist(String id) {
	return !isWallJustOnTheLeft(id);
    }
    
    /**
     * 
     * Returns whether the {@link VacuumWorldLocation} on the right w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * Right is relative to the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} on the {@link VacuumWorldLocation} to use as base location.
     * 
     * @return whether or not the {@link VacuumWorldLocation} on the right w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * 
     */
    public default boolean doesRightExist(String id) {
	return !isWallJustOnTheRight(id);
    }
    
    /**
     * 
     * Returns whether the {@link VacuumWorldLocation} ahead w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * Ahead is relative to the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} on the {@link VacuumWorldLocation} to use as base location.
     * 
     * @return whether or not the {@link VacuumWorldLocation} ahead w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * 
     */
    public default boolean doesForwardExist(String id) {
	return !isWallJustAhead(id);
    }
    
    /**
     * 
     * Returns whether the {@link VacuumWorldLocation} on the front-left w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * Front-left is relative to the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} on the {@link VacuumWorldLocation} to use as base location.
     * 
     * @return whether or not the {@link VacuumWorldLocation} on the front-left w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * 
     */
    public default boolean doesFrontLeftExist(String id) {
	return !isWallJustAhead(id) && !isWallJustOnTheLeft(id);
    }
    
    /**
     * 
     * Returns whether the {@link VacuumWorldLocation} on the front-right w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * Front-right is relative to the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} on the {@link VacuumWorldLocation} to use as base location.
     * 
     * @return whether or not the {@link VacuumWorldLocation} on the front-right w.r.t. the one where the {@link Actor} whose ID matches <code>id</code> resides exists in the perception.
     * 
     */
    public default boolean doesFrontRightExist(String id) {
	return !isWallJustAhead(id) && !isWallJustOnTheRight(id);
    }
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocations();
    
    /**
     * 
     * Returns all the {@link VacuumWorldCoordinates} of the {@link VacuumWorldLocation}s inside the perception.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldCoordinates} of the {@link VacuumWorldLocation}s inside the perception.
     * 
     */
    public abstract Set<VacuumWorldCoordinates> getAllCoordinates();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent}.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent}.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgents();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is <code>GREEN</code>.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is <code>GREEN</code>.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgents();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is <code>ORANGE</code>.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is <code>ORANGE</code>.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgents();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is <code>WHITE</code>.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldCleaningAgent} whose {@link AgentColor} is <code>WHITE</code>.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgents();
    
    /**
     * 
     * Same as {@link #getAllLocationsWithCleaningAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     * 
     * @param selfId the ID of the {@link Actor} whose location is to exclude from the returned value.
     * 
     * @return the same {@link Set} returned by {@link #getAllLocationsWithCleaningAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     *
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgentsExcludingSelf(String selfId);
    
    /**
     * 
     * Same as {@link #getAllLocationsWithGreenAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     * 
     * @param selfId the ID of the {@link Actor} whose location is to exclude from the returned value.
     * 
     * @return the same {@link Set} returned by {@link #getAllLocationsWithCleaningAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     *
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgentsExcludingSelf(String selfId);
    
    /**
     * 
     * Same as {@link #getAllLocationsWithOrangeAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     * 
     * @param selfId the ID of the {@link Actor} whose location is to exclude from the returned value.
     * 
     * @return the same {@link Set} returned by {@link #getAllLocationsWithCleaningAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     *
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgentsExcludingSelf(String selfId);
    
    /**
     * 
     * Same as {@link #getAllLocationsWithWhiteAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     * 
     * @param selfId the ID of the {@link Actor} whose location is to exclude from the returned value.
     * 
     * @return the same {@link Set} returned by {@link #getAllLocationsWithCleaningAgents()}, but excluding the {@link VacuumWorldLocation} containing the {@link Actor} whose ID matches <code>selfID</code>.
     *
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgentsExcludingSelf(String selfId);
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldUserAgent}.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldUserAgent}.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithUsers();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldAvatar}.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain a {@link VacuumWorldAvatar}.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithAvatars();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain an {@link ActiveBody}.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception which contain an {@link ActiveBody}.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithActiveBodies();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception, each one included only if the respective {@link VacuumWorldLocationAppearance#isEmpty()} method returns <code>true</code>.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception, each one included only if the respective {@link VacuumWorldLocationAppearance#isEmpty()} method returns <code>true</code>.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllEmptyLocations();
    
    /**
     * 
     * Returns all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception, each one included only if the respective {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method returns <code>true</code>.
     * 
     * @return a {@link Set} of all the {@link VacuumWorldLocationAppearance}s of the {@link VacuumWorldLocation}s inside the perception, each one included only if the respective {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method returns <code>true</code>.
     * 
     */
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsFreeFromActiveBodies();
    
    /**
     * 
     * Returns the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} which contains the {@link ActiveBody} whose ID matches <code>id</code>, if any.
     * 
     * @param id the ID of the {@link ActiveBody} whose {@link VacuumWorldLocation} this methods returns the {@link VacuumWorldLocationAppearance} of.
     * 
     * @return the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} which contains the {@link ActiveBody} whose ID matches <code>id</code>, if any, <code>null</code> otherwise.
     * 
     */
    public abstract VacuumWorldLocationAppearance getLocationFromActiveBodyId(String id);
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, has a wall in front of it.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, has a wall in front of it.
     * 
     */
    public default boolean isWallJustAhead(String id) {
	return getLocationFromActiveBodyId(id).isWallInFront(id);
    }
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, has a wall on its immediate left.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, has a wall on its immediate left.
     * 
     */
    public default boolean isWallJustOnTheLeft(String id) {
	return getLocationFromActiveBodyId(id).isWallOnTheLeft(id);
    }
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, has a wall on its immediate right.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, has a wall on its immediate right.
     * 
     */
    public default boolean isWallJustOnTheRight(String id) {
	return getLocationFromActiveBodyId(id).isWallOnTheRight(id);
    }
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, has a wall on its back.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, has a wall on its back.
     * 
     */
    public default boolean isWallJustBehind(String id) {
	return getLocationFromActiveBodyId(id).isWallBehind(id);
    }
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, can see a wall in front of itself at the straight-end of the {@link VacuumWorldLocation} ahead of its location.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, can see a wall in front of itself at the straight-end of the {@link VacuumWorldLocation} ahead of its location.
     * 
     */
    public abstract boolean isWallOneStepAhead(String id);
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, can see a wall on its left at the left-end of the {@link VacuumWorldLocation} on the left of its location.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, can see a wall on its left at the left-end of the {@link VacuumWorldLocation} on the left of its location.
     * 
     */
    public abstract boolean isWallOneStepOnTheLeft(String id);
    
    /**
     * 
     * Returns whether the {@link Actor} whose ID matches <code>id</code>, can see a wall on its right at the right-end of the {@link VacuumWorldLocation} on the right of its location.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not the {@link Actor} whose ID matches <code>id</code>, can see a wall on its right at the right-end of the {@link VacuumWorldLocation} on the right of its location.
     * 
     */
    public abstract boolean isWallOneStepOnTheRight(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} ahead of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Ahead is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} ahead of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isAheadEmpty(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the left of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the left of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isLeftEmpty(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the right of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the right of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isRightEmpty(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-left of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Forward-left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-left of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isForwardLeftEmpty(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-right of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Forward-right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isEmpty()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-right of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isForwardRightEmpty(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} ahead of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Ahead is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} ahead of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isAheadFreeFromActiveBodies(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the left of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the left of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isLeftFreeFromActiveBodies(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the right of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the right of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isRightFreeFromActiveBodies(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-left of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Forward-left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-left of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isForwardLeftFreeFromActiveBodies(String id);
    
    /**
     * 
     * Returns the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-right of the location of the {@link Actor} whose ID matches <code>id</code>.
     * Forward-right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return the return value of the {@link VacuumWorldLocationAppearance#isFreeFromActiveBodies()} method of the {@link VacuumWorldLocationAppearance} of the {@link VacuumWorldLocation} on the forward-right of of the location of the {@link Actor} whose ID matches <code>id</code>.
     * 
     */
    public abstract boolean isForwardRightFreeFromActiveBodies(String id);
    
    /**
     * 
     * Returns whether there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereDirt(String id);
    
    /**
     * 
     * Returns whether there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} ahead of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Ahead is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} ahead of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereDirtAhead(String id);
    
    /**
     * 
     * Returns whether there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereDirtOnTheLeft(String id);
    
    /**
     * 
     * Returns whether there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereDirtOnTheRight(String id);
    
    
    /**
     * 
     * Returns whether there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Forward-left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereDirtOnForwardLeft(String id);
    
    /**
     * 
     * Returns whether there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Forward-right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereDirtOnForwardRight(String id);
    
    /**
     * 
     * Returns the {@link AgentColor} of the {@link VacuumWorldCleaningAgent} whose ID matches <code>id</code>.
     * 
     * @param id id the ID of the {@link VacuumWorldCleaningAgent} of interest.
     * 
     * @return the {@link AgentColor} of the {@link VacuumWorldCleaningAgent} whose ID matches <code>id</code>.
     * 
     */
    public default AgentColor getAgentColorIfApplicable(String id) {
	VacuumWorldLocationAppearance location = getLocationFromActiveBodyId(id);
	
	return location.isACleaningAgentThere() ? location.getAgentAppearanceIfAny().getColor() : null;
    }
    
    /**
     * 
     * Returns whether there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereCompatibleDirt(String id);
    
    /**
     * 
     * Returns whether there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} ahead of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Ahead is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} ahead of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereCompatibleDirtAhead(String id);
    
    /**
     * 
     * Returns whether there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereCompatibleDirtOnTheLeft(String id);
    
    /**
     * 
     * Returns whether there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereCompatibleDirtOnTheRight(String id);
    
    /**
     * 
     * Returns whether there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Forward-left is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-left of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereCompatibleDirtOnForwardLeft(String id);
    
    /**
     * 
     * Returns whether there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * Forward-right is considered w.r.t. the {@link Actor}'s {@link Orientation}.
     * 
     * @param id the ID of the {@link Actor} of interest.
     * 
     * @return whether or not there is compatible {@link VacuumWorldDirt} on the {@link VacuumWorldLocation} on the forward-right of the one where the {@link Actor} whose ID matches <code>id</code> resides.
     * 
     */
    public abstract boolean isThereCompatibleDirtOnForwardRight(String id);
    
    /**
     * 
     * Returns the {@link VacuumWorldActorAppearance} of the {@link VacuumWorldActor} whose ID matches <code>id</code>.
     * 
     * @param id a {@link String} ID.
     * 
     * @return the {@link VacuumWorldActorAppearance} of the {@link VacuumWorldActor} whose ID matches <code>id</code>.
     * 
     */
    public default VacuumWorldActorAppearance getActorAppearance(String id) {
	return getLocationFromActiveBodyId(id).getActiveBodyAppearanceIfAny();
    }
}