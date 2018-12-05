package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public interface VacuumWorldPerceptiveEntity {

    /**
     * 
     * Gets the {@link VacuumWorldPerception} that the agent has received from the {@link VacuumWorldEnvironment} at the end of the previous cycle.
     * 
     * @return the {@link VacuumWorldPerception} that the agent has received from the {@link VacuumWorldEnvironment} at the end of the previous cycle.
     * 
     */
    public abstract VacuumWorldPerception getPerception();

    /**
     * 
     * Returns the {@link ActionResult} of the {@link Action} that was executed during the latest cycle.
     * 
     * @return the {@link ActionResult} of the {@link Action} that was executed during the latest cycle.
     * 
     */
    public default ActionResult getLatestActionResult() {
	return getPerception() == null ? null : getPerception().getResult();
    }
    
    /**
     * 
     * Returns whether new messages are available (i.e., whether at least an actor sent a message to this actor during last cycle).
     * 
     * @return whether or not new messages are available.
     * 
     */
    public abstract boolean hasNewMessages();
    
    /**
     * 
     * Returns a (possibly empty) {@link List} of {@link VacuumWorldSpeechPerception} messages sent by other actors to this actor during last cycle.
     * 
     * @return a (possibly empty) {@link List} of {@link VacuumWorldSpeechPerception} messages.
     * 
     */
    public abstract List<VacuumWorldSpeechPerception> getMessages();
    
    /**
     * 
     * Returns the body ID of self.
     * 
     * @return a {@link String} consisting of the body ID of self.
     * 
     */
    public abstract String getBodyId();

    /**
     * 
     * Returns whether there is an available {@link VacuumWorldPerception}, or not.
     * 
     * @return whether or not there is an available {@link VacuumWorldPerception}.
     * 
     */
    public default boolean hasPerception() {
	return getPerception() != null;
    }
    
    /**
     * 
     * Returns whether a {@link VacuumWorldLocation} whose {@link VacuumWorldCoordinates} match <code>coordinates</code> exists within the perception.
     * 
     * @param coordinates a {@link VacuumWorldCoordinates} object.
     * 
     * @return whether or not a {@link VacuumWorldLocation} whose {@link VacuumWorldCoordinates} match <code>coordinates</code> exists within the perception.
     * 
     */
    public default boolean doesThisLocationExistInThePerception(VacuumWorldCoordinates coordinates) {
	return getPerception().getAppearance().doesSuchLocationExistInPerception(coordinates);
    }
    
    /**
     * Checks if this agent is able to clean the given dirt. An example use:
     * canClean(getDirt()) checks if the agent is able to clean the dirt that it is
     * currently on top of. Recall that,
     * <ul>
     * <li>White agents can clean orange and green dirt
     * <li>Green agents can only clean green dirt
     * <li>Orange agents can only clean orange dirt
     * </ul>
     * 
     * @param dirt
     *                 : to check
     * @return true if this agent is able to clean the dirt, false otherwise.
     */
    public default boolean canClean(VacuumWorldDirtAppearance dirt) {
	if (dirt != null) {
	    return dirt.getColor().canBeCleanedBy(getAgent().getColor());
	}
	else {
	    return false;
	}
    }

    /**
     * Gets the appearance of dirt that is at this agents current location (or null
     * if there is no such dirt).
     * 
     * @return the appearance of the dirt that is at this agents current location
     *         (or null).
     */
    public default VacuumWorldDirtAppearance getDirt() {
	return this.getLocation().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of dirt that is to the left of this agent (or null if
     * there is no such dirt, or the target location does not exist within the perception.).
     * 
     * @return the appearance of dirt that is to the left of this agent (or null).
     */
    public default VacuumWorldDirtAppearance getDirtLeft() {
	return isDirtLeft() ? this.getLocationLeft().getDirtAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of dirt that is to the right of this agent (or null if
     * there is no such dirt, or the target location does not exist within the perception.).
     * 
     * @return the appearance of dirt that is to the right of this agent (or null).
     */
    public default VacuumWorldDirtAppearance getDirtRight() {
	return isDirtRight() ?  this.getLocationRight().getDirtAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of dirt that is immediately forward of this agent (or
     * null if there is no such dirt, or the target location does not exist within the perception.).
     * 
     * @return the appearance of dirt that is to the immediately forward of this
     *         agent (or null).
     */
    public default VacuumWorldDirtAppearance getDirtForward() {
	return isDirtForward() ? this.getLocationForward().getDirtAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of dirt that is forward left of this agent (or null if
     * there is no such dirt, or the target location does not exist within the perception.).
     * 
     * @return the appearance of dirt that is forward left of this agent (or null).
     */
    public default VacuumWorldDirtAppearance getDirtForwardLeft() {
	return isDirtForwardLeft() ? this.getLocationForwardLeft().getDirtAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of dirt that is forward right of this agent (or null if
     * there is no such dirt, or the target location does not exist within the perception.).
     * 
     * @return the appearance of dirt that is forward right of this agent (or null).
     */
    public default VacuumWorldDirtAppearance getDirtForwardRight() {
	return isDirtForwardRight() ? this.getLocationForwardRight().getDirtAppearanceIfAny() : null;
    }
    
    /**
     * Gets the appearance of this agent.
     * 
     * @return the appearance of this agent.
     */
    public default VacuumWorldActorAppearance getAgent() {
	return this.getLocation().getActiveBodyAppearanceIfAny();
    }

    /**
     * Gets the appearance of the actor (agent of user) to the left of this agent (or null if there
     * is no such actor, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the actor (agent of user) to the left of this agent (or null).
     */
    public default VacuumWorldActorAppearance getActorLeft() {
	return isAgentLeft() || isUserLeft() ? this.getLocationLeft().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the actor (agent of user) to the right of this agent (or null if there
     * is no such actor, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the actor (agent of user) to the right of this agent (or null).
     */

    public default VacuumWorldActorAppearance getActorRight() {
	return isAgentRight() || isUserRight() ? this.getLocationRight().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the actor (agent of user) immediately forward of this agent (or null
     * if there is no such actor, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the actor (agent of user) immediately forward of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getActorForward() {
	return isAgentForward() || isUserForward() ? this.getLocationForward().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the actor (agent of user) to the forward left of this agent (or null
     * if there is no such actor, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the actor (agent of user) to the forward left of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getActorForwardLeft() {
	return isAgentForwardLeft() || isUserForwardLeft() ? this.getLocationForwardLeft().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the actor (agent of user) to the forward right of this agent (or null
     * if there is no such actor, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the actor (agent of user) to the forward right of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getActorForwardRight() {
	return isAgentForwardRight() || isUserForwardRight() ? this.getLocationForwardRight().getActiveBodyAppearanceIfAny() : null;
    }
    
    /**
     * Gets the appearance of the agent to the left of this agent (or null if there
     * is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the agent to the left of this agent (or null).
     */
    public default VacuumWorldActorAppearance getAgentLeft() {
	return isAgentLeft() ? this.getLocationLeft().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the agent to the right of this agent (or null if there
     * is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the agent to the right of this agent (or null).
     */

    public default VacuumWorldActorAppearance getAgentRight() {
	return isAgentRight() ? this.getLocationRight().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the agent immediately forward of this agent (or null
     * if there is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the agent immediately forward of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getAgentForward() {
	return isAgentForward() ? this.getLocationForward().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the agent to the forward left of this agent (or null
     * if there is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the agent to the forward left of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getAgentForwardLeft() {
	return isAgentForwardLeft() ? this.getLocationForwardLeft().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the agent to the forward right of this agent (or null
     * if there is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the agent to the forward right of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getAgentForwardRight() {
	return isAgentForwardRight() ? this.getLocationForwardRight().getActiveBodyAppearanceIfAny() : null;
    }
    
    /**
     * Gets the appearance of the user to the left of this agent (or null if there
     * is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the user to the left of this agent (or null).
     */
    public default VacuumWorldActorAppearance getUserLeft() {
	return isUserLeft() ? this.getLocationLeft().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the user to the right of this agent (or null if there
     * is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the user to the right of this agent (or null).
     */

    public default VacuumWorldActorAppearance getUserRight() {
	return isUserRight() ? this.getLocationRight().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the user immediately forward of this agent (or null
     * if there is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the user immediately forward of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getUserForward() {
	return isUserForward() ? this.getLocationForward().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the user to the forward left of this agent (or null
     * if there is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the user to the forward left of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getUserForwardLeft() {
	return isUserForwardLeft() ? this.getLocationForwardLeft().getActiveBodyAppearanceIfAny() : null;
    }

    /**
     * Gets the appearance of the user to the forward right of this agent (or null
     * if there is no such agent, or the target location does not exist within the perception.)
     * 
     * @return the appearance of the user to the forward right of this agent (or
     *         null).
     */

    public default VacuumWorldActorAppearance getUserForwardRight() {
	return isUserForwardRight() ? this.getLocationForwardRight().getActiveBodyAppearanceIfAny() : null;
    }
    
    /**
     * Is this agent currently facing north?
     */
    public default boolean isOrientationNorth() {
	return Orientation.NORTH.equals(getOrientation());
    }

    /**
     * Is this agent currently facing south?
     */
    public default boolean isOrientationSouth() {
	return Orientation.SOUTH.equals(getOrientation());
    }

    /**
     * Is this agent currently facing east?
     */
    public default boolean isOrientationEast() {
	return Orientation.EAST.equals(getOrientation());
    }

    /**
     * Is this agent currently facing west?
     */
    public default boolean isOrientationWest() {
	return Orientation.WEST.equals(getOrientation());
    }

    /**
     * Gets the {@link Orientation} of this agent (the direction is it facing)
     * 
     * @return the {@link Orientation} of this agent
     */
    public default Orientation getOrientation() {
	return getLocation().getActiveBodyOrientation();
    }

    /**
     * Gets all of the current perception information in the form of a map from
     * coordinates to locations.
     * 
     * @return A {@link map} from {@link VacuumWorldCoordinates} to
     *         {@link VacuumWorldLocationAppearance}.
     */
    public default Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> getGrid() {
	return getPerception().getAppearance().getGrid();
    }

    /**
     * Number of locations present in the current perception, (i.e. how many squares
     * there are, this depends on whether the agent is at the edge/corner of the
     * grid)
     * 
     * @return number of locations present
     */
    public default int locationCount() {
	return getPerception().getAppearance().countNumberOfLocations();
    }

    /**
     * Gets all of the locations present in the current perception.
     * 
     * @return locations
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocations() {
	return getPerception().getAppearance().getAllLocations();
    }

    /**
     * Gets all of the coordinates present in current perception (each location has
     * a coordinate).
     * 
     * @return all of the coordinates present in current perception
     */
    public default Set<VacuumWorldCoordinates> getAllCoordinates() {
	return getPerception().getAppearance().getAllCoordinates();
    }

    /**
     * Gets all of the locations that have a cleaning agent present on them from the
     * current perception.
     * 
     * @return all of the locations that have a cleaning agent present on them from
     *         the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgents() {
	return getPerception().getAppearance().getAllLocationsWithCleaningAgents();
    }

    /**
     * Gets all of the locations that have a green cleaning agent present on them
     * from the current perception.
     * 
     * @return all of the locations that have a green cleaning agent present on them
     *         from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgents() {
	return getPerception().getAppearance().getAllLocationsWithGreenAgents();
    }

    /**
     * Gets all of the locations that have a orange cleaning agent present on them
     * from the current perception.
     * 
     * @return all of the locations that have a orange cleaning agent present on
     *         them from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgents() {
	return getPerception().getAppearance().getAllLocationsWithOrangeAgents();
    }

    /**
     * Gets all of the locations that have a white cleaning agent present on them
     * from the current perception.
     * 
     * @return all of the locations that have a white cleaning agents present on
     *         them from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgents() {
	return getPerception().getAppearance().getAllLocationsWithWhiteAgents();
    }

    /**
     * Gets all of the locations that have a user present on them from the current
     * perception.
     * 
     * @return all of the locations that have a user present on them from the
     *         current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithUsers() {
	return getPerception().getAppearance().getAllLocationsWithUsers();
    }

    /**
     * Gets all of the locations that are empty (i.e. no agent, user or dirt) from
     * the current perception.
     * 
     * @return all of the locations that are empty (i.e. no agent, user or dirt)
     *         from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllEmptyLocations() {
	return getPerception().getAppearance().getAllEmptyLocations();
    }

    /**
     * Gets all of the locations that have a dirt present on them from the current
     * perception.
     * 
     * @return all of the locations that have a dirt present on them from the
     *         current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllDirtyLocations() {
	return getAllLocations().stream().filter(VacuumWorldLocationAppearance::isDirtThere).collect(Collectors.toSet());
    }

    /**
     * Is there a wall immediately forward?
     */
    public default boolean isWallForward() {
	return getPerception().getAppearance().isWallJustAhead(this.getBodyId());
    }

    /**
     * Is there a wall immediately left?
     */
    public default boolean isWallLeft() {
	return getPerception().getAppearance().isWallJustOnTheLeft(this.getBodyId());
    }

    /**
     * Is there a wall immediately right?
     */
    public default boolean isWallRight() {
	return getPerception().getAppearance().isWallJustOnTheRight(this.getBodyId());
    }

    /**
     * Is the location empty immediately forward?
     */
    public default boolean isEmptyForward() {
	return getPerception().getAppearance().isAheadEmpty(this.getBodyId());
    }

    /**
     * Is the location empty immediately left?
     */
    public default boolean isEmptyLeft() {
	return getPerception().getAppearance().isLeftEmpty(this.getBodyId());
    }

    /**
     * Is the location empty immediately right?
     */
    public default boolean isEmptyRight() {
	return getPerception().getAppearance().isRightEmpty(this.getBodyId());
    }

    /**
     * Is the location empty forward left?
     */
    public default boolean isEmptyForwardLeft() {
	return getPerception().getAppearance().isForwardLeftEmpty(this.getBodyId());
    }

    /**
     * Is the location empty forward right?
     */
    public default boolean isEmptyForwardRight() {
	return getPerception().getAppearance().isForwardRightEmpty(this.getBodyId());
    }

    /**
     * Is the current location dirty?
     */
    public default boolean isDirt() {
	return getPerception().getAppearance().isThereDirt(this.getBodyId());
    }

    /**
     * Is the location dirty immediately forward?
     */
    public default boolean isDirtForward() {
	return getPerception().getAppearance().isThereDirtAhead(this.getBodyId());
    }

    /**
     * Is the location dirty immediately left?
     */
    public default boolean isDirtLeft() {
	return getPerception().getAppearance().isThereDirtOnTheLeft(this.getBodyId());
    }

    /**
     * Is the location dirty immediately left?
     */
    public default boolean isDirtRight() {
	return getPerception().getAppearance().isThereDirtOnTheRight(this.getBodyId());
    }

    /**
     * Is the location dirty forward left?
     */
    public default boolean isDirtForwardLeft() {
	return getPerception().getAppearance().isThereDirtOnForwardLeft(this.getBodyId());
    }

    /**
     * Is the location dirty forward right?
     */
    public default boolean isDirtForwardRight() {
	return getPerception().getAppearance().isThereDirtOnForwardRight(this.getBodyId());
    }

    /**
     * Is there another agent at this agents immediate forward location?
     */
    public default boolean isAgentForward() {
	return getPerception().getAppearance().isThereACleaningAgentForward(this.getBodyId());
    }

    /**
     * Is there another agent at this agents immediate left location?
     */
    public default boolean isAgentLeft() {
	return getPerception().getAppearance().isThereACleaningAgentOnTheLeft(this.getBodyId());
    }

    /**
     * Is there another agent at this agents immediate right location?
     */
    public default boolean isAgentRight() {
	return getPerception().getAppearance().isThereACleaningAgentOnTheRight(this.getBodyId());
    }

    /**
     * Is there another agent at this agents forward right location?
     */
    public default boolean isAgentForwardRight() {
	return getPerception().getAppearance().isThereACleaningAgentOnForwardRight(this.getBodyId());
    }

    /**
     * Is there another agent at this agents forward left location?
     */
    public default boolean isAgentForwardLeft() {
	return getPerception().getAppearance().isThereACleaningAgentOnForwardLeft(this.getBodyId());
    }

    /**
     * Is there a user at this agents immediate forward location?
     */
    public default boolean isUserForward() {
	return getPerception().getAppearance().isThereAUserForward(this.getBodyId());
    }

    /**
     * Is there a user at this agents immediate left location?
     */
    public default boolean isUserLeft() {
	return getPerception().getAppearance().isThereAUserOnTheLeft(this.getBodyId());
    }

    /**
     * Is there a user at this agents immediate right location?
     */
    public default boolean isUserRight() {
	return getPerception().getAppearance().isThereAUserOnTheRight(this.getBodyId());
    }

    /**
     * Is there a user at this agents forward right location?
     */
    public default boolean isUserForwardRight() {
	return getPerception().getAppearance().isThereAUserOnForwardRight(this.getBodyId());
    }

    /**
     * Is there a user at this agents forward left location?
     */
    public default boolean isUserForwardLeft() {
	return getPerception().getAppearance().isThereAUserOnForwardLeft(this.getBodyId());
    }

    /**
     * Gets the coordinates of an agent (or user) given its appearance. It will return null, if the agent or user is not within the perception.
     * 
     * @param appearance
     *                       of an agent (or user)
     * @return the coordinates
     */
    public default VacuumWorldCoordinates getCoordinatesOf(VacuumWorldActorAppearance appearance) {
	VacuumWorldLocationAppearance a = getPerception().getAppearance().getLocationFromActiveBodyId(appearance.getId());
	
	return a == null ? null : a.getCoordinates();
    }

    /**
     * Gets the current coordinates of this agent's location.
     * 
     * @return the current coordinates of this agent's location.
     */
    public default VacuumWorldCoordinates getCoordinates() {
	return getPerception().getAppearance().getLocationFromActiveBodyId(this.getBodyId()).getCoordinates();
    }

    /**
     * Gets the current coordinates immediately left of this agent.<br /><br />
     * While this method always returns a valid {@link VacuumWorldCoordinates} object, please note that the existence (either within the perception, or in the real environment) of a location whose coordinates match the return value of this method is not guaranteed, nor checked by this method.
     * 
     * @return the current coordinates immediately left of this agent.
     */
    public default VacuumWorldCoordinates getCoordinatesLeft() {
	return getCoordinates().getLeftCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates immediately right of this agent.<br /><br />
     * While this method always returns a valid {@link VacuumWorldCoordinates} object, please note that the existence (either within the perception, or in the real environment) of a location whose coordinates match the return value of this method is not guaranteed, nor checked by this method.
     * 
     * @return the current coordinates immediately right of this agent.
     */
    public default VacuumWorldCoordinates getCoordinatesRight() {
	return getCoordinates().getRightCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates immediately forward of this agent.<br /><br />
     * While this method always returns a valid {@link VacuumWorldCoordinates} object, please note that the existence (either within the perception, or in the real environment) of a location whose coordinates match the return value of this method is not guaranteed, nor checked by this method.
     * 
     * @return the current coordinates immediately forward of this agent.
     */
    public default VacuumWorldCoordinates getCoordinatesForward() {
	return getCoordinates().getForwardCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates forward left of this agent.<br /><br />
     * While this method always returns a valid {@link VacuumWorldCoordinates} object, please note that the existence (either within the perception, or in the real environment) of a location whose coordinates match the return value of this method is not guaranteed, nor checked by this method.
     * 
     * @return the current coordinates forward left of this agent.
     */
    public default VacuumWorldCoordinates getCoordinatesForwardLeft() {
	return getCoordinates().getForwardLeftCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates forward right of this agent.<br /><br />
     * While this method always returns a valid {@link VacuumWorldCoordinates} object, please note that the existence (either within the perception, or in the real environment) of a location whose coordinates match the return value of this method is not guaranteed, nor checked by this method.
     * 
     * @return the current coordinates forward right of this agent.
     */
    public default VacuumWorldCoordinates getCoordinatesForwardRight() {
	return getCoordinates().getForwardRightCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current location of this agent.<br /><br />
     * While this method always returns a valid {@link VacuumWorldCoordinates} object, please note that the existence (either within the perception, or in the real environment) of a location whose coordinates match the return value of this method is not guaranteed, nor checked by this method.
     * 
     * @return the current location of this agent.
     */
    public default VacuumWorldLocationAppearance getLocation() {
	return this.getGrid().get(getCoordinates());
    }

    /**
     * Gets the current location immediately left of this agent, if it exists within the perception, otherwise <code>null</code>.
     * 
     * @return the current location immediately left of this agent, if it exists within the perception, otherwise <code>null</code>.
     */
    public default VacuumWorldLocationAppearance getLocationLeft() {
	return doesThisLocationExistInThePerception(getCoordinatesLeft()) ? this.getGrid().get(getCoordinatesLeft()) : null;
    }

    /**
     * Gets the current location immediately right of this agent, if it exists within the perception, otherwise <code>null</code>.
     * 
     * @return the current location immediately right of this agent, if it exists within the perception, otherwise <code>null</code>.
     */
    public default VacuumWorldLocationAppearance getLocationRight() {
	return doesThisLocationExistInThePerception(getCoordinatesRight()) ? this.getGrid().get(getCoordinatesRight()) : null;
    }

    /**
     * Gets the current location immediately forward of this agent, if it exists within the perception, otherwise <code>null</code>.
     * 
     * @return the current location immediately forward of this agent, if it exists within the perception, otherwise <code>null</code>.
     */
    public default VacuumWorldLocationAppearance getLocationForward() {
	return doesThisLocationExistInThePerception(getCoordinatesForward()) ? this.getGrid().get(getCoordinatesForward()) : null;
    }

    /**
     * Gets the current location forward left of this agent, if it exists within the perception, otherwise <code>null</code>.
     * 
     * @return the current location forward left of this agent, if it exists within the perception, otherwise <code>null</code>.
     */
    public default VacuumWorldLocationAppearance getLocationForwardLeft() {
	return doesThisLocationExistInThePerception(getCoordinatesForwardLeft()) ? this.getGrid().get(getCoordinatesForwardLeft()) : null;
    }

    /**
     * Gets the current location forward right of this agent, if it exists within the perception, otherwise <code>null</code>.
     * 
     * @return the current location forward right of this agent, if it exists within the perception, otherwise <code>null</code>.
     */
    public default VacuumWorldLocationAppearance getLocationForwardRight() {
	return doesThisLocationExistInThePerception(getCoordinatesForwardRight()) ? this.getGrid().get(getCoordinatesForwardRight()) : null;
    }
}