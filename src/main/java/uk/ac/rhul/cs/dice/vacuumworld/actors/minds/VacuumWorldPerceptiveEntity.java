package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public interface VacuumWorldPerceptiveEntity {

    /**
     * 
     * Gets the {@link VacuumWorldPerception} that the agent has received from the
     * {@link VacuumWorldEnvironment} at the end of the previous cycle.<br/>
     * <br/>
     * BEWARE: it can be <code>null</code>, ant it WILL be <code>null</code> during
     * the first cycle.
     * 
     * @return the {@link VacuumWorldPerception} that the agent has received from
     *         the {@link VacuumWorldEnvironment} at the end of the previous cycle.
     * 
     */
    public abstract VacuumWorldPerception getPerception();

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
	} else {
	    return false;
	}
    }

    /**
     * Gets the appearance of dirt that is at this agents current location (or null
     * if there is no such dirt).
     * 
     * @return the appearance of the dirt that is at this agents current location
     *         (or null)
     */
    public default VacuumWorldDirtAppearance getDirt() {
	return this.getLocation().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of dirt that is to the left of this agent (or null if
     * there is no such dirt).
     * 
     * @return the appearance of dirt that is to the left of this agent (or null)
     */
    public default VacuumWorldDirtAppearance getDirtLeft() {
	return this.getLocationLeft().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of dirt that is to the right of this agent (or null if
     * there is no such dirt).
     * 
     * @return the appearance of dirt that is to the right of this agent (or null)
     */
    public default VacuumWorldDirtAppearance getDirtRight() {
	return this.getLocationRight().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of dirt that is immediately forward of this agent (or
     * null if there is no such dirt).
     * 
     * @return the appearance of dirt that is to the immediately forward of this
     *         agent (or null)
     */
    public default VacuumWorldDirtAppearance getDirtForward() {
	return this.getLocationForward().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of dirt that is forward left of this agent (or null if
     * there is no such dirt).
     * 
     * @return the appearance of dirt that is forward left of this agent (or null)
     */
    public default VacuumWorldDirtAppearance getDirtForwardLeft() {
	return this.getLocationForardLeft().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of dirt that is forward right of this agent (or null if
     * there is no such dirt).
     * 
     * @return the appearance of dirt that is forward right of this agent (or null)
     */
    public default VacuumWorldDirtAppearance getDirtForwardRight() {
	return this.getLocationForardRight().getDirtAppearanceIfAny();
    }

    /**
     * Gets the appearance of this agent.
     * 
     * @return the appearance of this agent
     */
    public default VacuumWorldActorAppearance getAgent() {
	return this.getLocation().getActiveBodyAppearanceIfAny();
    }

    /**
     * Gets the appearance of the agent to the left of this agent (or null if there
     * is no such agent)
     * 
     * @return the appearance of the agent to the left of this agent (or null)
     */
    public default VacuumWorldActorAppearance getAgentLeft() {
	return this.getLocationLeft().getActiveBodyAppearanceIfAny();
    }

    /**
     * Gets the appearance of the agent to the right of this agent (or null if there
     * is no such agent)
     * 
     * @return the appearance of the agent to the right of this agent (or null)
     */

    public default VacuumWorldActorAppearance getAgentRight() {
	return this.getLocationRight().getActiveBodyAppearanceIfAny();
    }

    /**
     * Gets the appearance of the agent immediately forward of this agent (or null
     * if there is no such agent)
     * 
     * @return the appearance of the agent immediately forward of this agent (or
     *         null)
     */

    public default VacuumWorldActorAppearance getAgentForward() {
	return this.getLocationForward().getActiveBodyAppearanceIfAny();
    }

    /**
     * Gets the appearance of the agent to the forward left of this agent (or null
     * if there is no such agent)
     * 
     * @return the appearance of the agent to the forward left of this agent (or
     *         null)
     */

    public default VacuumWorldActorAppearance getAgentForwardLeft() {
	return this.getLocationForardLeft().getActiveBodyAppearanceIfAny();
    }

    /**
     * Gets the appearance of the agent to the forward right of this agent (or null
     * if there is no such agent)
     * 
     * @return the appearance of the agent to the forward right of this agent (or
     *         null)
     */

    public default VacuumWorldActorAppearance getAgentForwardRight() {
	return this.getLocationForardRight().getActiveBodyAppearanceIfAny();
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
	return !isWallForward() && getPerception().getAppearance().isThereDirtAhead(this.getBodyId());
    }

    /**
     * Is the location dirty immediately left?
     */
    public default boolean isDirtLeft() {
	return !isWallLeft() && getPerception().getAppearance().isThereDirtOnTheLeft(this.getBodyId());
    }

    /**
     * Is the location dirty immediately left?
     */
    public default boolean isDirtRight() {
	return !isWallRight() && getPerception().getAppearance().isThereDirtOnTheRight(this.getBodyId());
    }

    /**
     * Is the location dirty forward left?
     */
    public default boolean isDirtForwardLeft() {
	return !isWallForward() && !isWallLeft() && getPerception().getAppearance().isThereDirtOnForwardLeft(this.getBodyId());
    }

    /**
     * Is the location dirty forward right?
     */
    public default boolean isDirtForwardRight() {
	return !isWallForward() && !isWallRight() && getPerception().getAppearance().isThereDirtOnForwardRight(this.getBodyId());
    }

    /**
     * Is there another agent at this agents immediate forward location?
     */
    public default boolean isAgentForward() {
	return !isWallForward() && getPerception().getAppearance().getGrid().get(this.getCoordinatesForward()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents immediate left location?
     */
    public default boolean isAgentLeft() {
	return !isWallLeft() && getPerception().getAppearance().getGrid().get(this.getCoordinatesLeft()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents immediate right location?
     */
    public default boolean isAgentRight() {
	return !isWallRight() && getPerception().getAppearance().getGrid().get(this.getCoordinatesRight()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents forward right location?
     */
    public default boolean isAgentForwardRight() {
	return !isWallForward() && !isWallRight() && getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardRight()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents forward left location?
     */
    public default boolean isAgentForwardLeft() {
	return !isWallForward() && !isWallLeft() && getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardLeft()).isACleaningAgentThere();
    }

    /**
     * Is there a user at this agents immediate forward location?
     */
    public default boolean isUserForward() {
	return !isWallForward() && getPerception().getAppearance().getGrid().get(this.getCoordinatesForward()).isAUserThere();
    }

    /**
     * Is there a user at this agents immediate left location?
     */
    public default boolean isUserLeft() {
	return !isWallLeft() && getPerception().getAppearance().getGrid().get(this.getCoordinatesLeft()).isAUserThere();
    }

    /**
     * Is there a user at this agents immediate right location?
     */
    public default boolean isUserRight() {
	return !isWallRight() && getPerception().getAppearance().getGrid().get(this.getCoordinatesRight()).isAUserThere();
    }

    /**
     * Is there a user at this agents forward right location?
     */
    public default boolean isUserForwardRight() {
	return !isWallForward() && !isWallRight() && getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardRight()).isAUserThere();
    }

    /**
     * Is there a user at this agents forward left location?
     */
    public default boolean isUserForwardLeft() {
	return !isWallForward() && !isWallLeft() && getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardLeft()).isAUserThere();
    }

    /**
     * Gets the coordinates of an agent (or user) given its appearance.
     * 
     * @param appearance
     *                       of an agent (or user)
     * @return the coordinates
     */
    public default VacuumWorldCoordinates getCoordinatesOf(VacuumWorldActorAppearance appearance) {
	return getPerception().getAppearance().getLocationFromActiveBodyId(appearance.getId()).getCoordinates();
    }

    /**
     * Gets the current coordinates of this agent
     * 
     * @return the current coordinates of this agent
     */
    public default VacuumWorldCoordinates getCoordinates() {
	return getPerception().getAppearance().getLocationFromActiveBodyId(this.getBodyId()).getCoordinates();
    }

    /**
     * Gets the current coordinates immediately left of this agent
     * 
     * @return the current coordinates immediately left of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesLeft() {
	return getCoordinates().getLeftCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates immediately right of this agent
     * 
     * @return the current coordinates immediately right of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesRight() {
	return getCoordinates().getRightCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates immediately forward of this agent
     * 
     * @return the current coordinates immediately forward of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesForward() {
	return getCoordinates().getForwardCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates forward left of this agent
     * 
     * @return the current coordinates forward left of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesForwardLeft() {
	return getCoordinates().getForwardLeftCoordinates(
		getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates forward right of this agent
     * 
     * @return the current coordinates forward right of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesForwardRight() {
	return getCoordinates().getForwardRightCoordinates(getPerception().getAppearance().getActorAppearance(this.getBodyId()).getOrientation());
    }

    /**
     * Gets the current location of this agent
     * 
     * @return the current location of this agent
     */
    public default VacuumWorldLocationAppearance getLocation() {
	return this.getGrid().get(getCoordinates());
    }

    /**
     * Gets the current location immediately left of this agent
     * 
     * @return the current location immediately left of this agent
     */
    public default VacuumWorldLocationAppearance getLocationLeft() {
	return this.getGrid().get(getCoordinatesLeft());
    }

    /**
     * Gets the current location immediately right of this agent
     * 
     * @return the current location immediately right of this agent
     */
    public default VacuumWorldLocationAppearance getLocationRight() {
	return this.getGrid().get(getCoordinatesRight());
    }

    /**
     * Gets the current location immediately forward of this agent
     * 
     * @return the current location immediately forward of this agent
     */
    public default VacuumWorldLocationAppearance getLocationForward() {
	return this.getGrid().get(getCoordinatesForward());
    }

    /**
     * Gets the current location forward left of this agent
     * 
     * @return the current location forward left of this agent
     */
    public default VacuumWorldLocationAppearance getLocationForardLeft() {
	return this.getGrid().get(getCoordinatesForwardLeft());
    }

    /**
     * Gets the current location forward right of this agent
     * 
     * @return the current location forward right of this agent
     */
    public default VacuumWorldLocationAppearance getLocationForardRight() {
	return this.getGrid().get(getCoordinatesForwardRight());
    }
}