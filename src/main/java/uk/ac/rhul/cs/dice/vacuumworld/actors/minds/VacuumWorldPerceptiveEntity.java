package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;

public interface VacuumWorldPerceptiveEntity {
    
    public abstract VacuumWorldPerception getPerception();

    public abstract String getBodyId();

    public default boolean hasPerception() {
	return getPerception() != null;
    }
    
    public default boolean doesLocationExist(VacuumWorldCoordinates coordinates) {
	return getGrid().containsKey(coordinates);
    }
    
    public default VacuumWorldLocationAppearance getLocationIfExists(VacuumWorldCoordinates coordinates) {
	return getGrid().get(coordinates);
    }
    
    public default VacuumWorldLocationAppearance getLocationFromActiveBodyId(String id) {
	return getGrid().values().stream().filter(location -> location.containsSuchActiveBody(id)).findFirst().orElse(null);
    }
    
    public default VacuumWorldCoordinates getCoordinatesFromActiveBodyId(String id) {
	return getLocationFromActiveBodyId(id).getCoordinates();
    }
    
    public default AgentColor getAnyAgentColorIfApplicable(String id) {
	VacuumWorldLocationAppearance location = getLocationFromActiveBodyId(id);
	
	return location.isACleaningAgentThere() ? location.getAgentAppearanceIfAny().getColor() : null;
    }
    
    public default AgentColor getSelfColorIfApplicable() {
	return getAnyAgentColorIfApplicable(getBodyId());
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
	return getSelfLocation().getActiveBodyOrientation();
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
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgentsIncludingSelf() {
	return getPerception().getAppearance().getAllLocationsWithCleaningAgents();
    }
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgentsExcludingSelf() {
	return getPerception().getAppearance().getAllLocationsWithCleaningAgentsExcludingSelf(getBodyId());
    }

    /**
     * Gets all of the locations that have a green cleaning agent present on them
     * from the current perception.
     * 
     * @return all of the locations that have a green cleaning agent present on them
     *         from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgentsIncludingSelfIfApplicable() {
	return getPerception().getAppearance().getAllLocationsWithGreenAgents();
    }
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgentsExcludingSelf() {
	return getPerception().getAppearance().getAllLocationsWithGreenAgentsExcludingSelf(getBodyId());
    }

    /**
     * Gets all of the locations that have a orange cleaning agent present on them
     * from the current perception.
     * 
     * @return all of the locations that have a orange cleaning agent present on
     *         them from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgentsIncludingSelfIfApplicable() {
	return getPerception().getAppearance().getAllLocationsWithOrangeAgents();
    }
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgentsExcludingSelf() {
	return getPerception().getAppearance().getAllLocationsWithOrangeAgentsExcludingSelf(getBodyId());
    }

    /**
     * Gets all of the locations that have a white cleaning agent present on them
     * from the current perception.
     * 
     * @return all of the locations that have a white cleaning agents present on
     *         them from the current perception
     */
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgentsIncludingSelfIfApplicable() {
	return getPerception().getAppearance().getAllLocationsWithWhiteAgents();
    }
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgentsExcludingSelf() {
	return getPerception().getAppearance().getAllLocationsWithWhiteAgentsExcludingSelf(getBodyId());
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
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithAvatars() {
	return getPerception().getAppearance().getAllLocationsWithAvatars();
    }
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithActiveBodies() {
	return getPerception().getAppearance().getAllLocationsWithActiveBodies();
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
    
    public default Set<VacuumWorldLocationAppearance> getAllNonEmptyLocations() {
	return getPerception().getAppearance().getAllNonEmptyLocations();
    }

    public default Set<VacuumWorldLocationAppearance> getAllLocationsFreeFromActiveBodies() {
	return getPerception().getAppearance().getAllLocationsFreeFromActiveBodies();
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
    
    public default Set<VacuumWorldLocationAppearance> getAllLocationsWithoutDirt() {
	return getAllLocations().stream().filter(VacuumWorldLocationAppearance::isFreeFromDirt).collect(Collectors.toSet());
    }

    /* ########## Wall API ########## */
    
    /**
     * Is there a wall immediately forward?
     */
    public default boolean isWallForward() {
	return getPerception().getAppearance().isWallJustAhead(getBodyId());
    }

    /**
     * Is there a wall immediately left?
     */
    public default boolean isWallLeft() {
	return getPerception().getAppearance().isWallJustOnTheLeft(getBodyId());
    }

    /**
     * Is there a wall immediately right?
     */
    public default boolean isWallRight() {
	return getPerception().getAppearance().isWallJustOnTheRight(getBodyId());
    }
    
    /**
     * Is there a wall immediately behind?
     */
    public default boolean isWallBehind() {
	return getPerception().getAppearance().isWallJustBehind(getBodyId());
    }
    
    public default boolean isWallOneStepAhead() {
	return getPerception().getAppearance().isWallOneStepAhead(getBodyId());
    }

    public default boolean isWallOneStepOnTheLeft() {
	return getPerception().getAppearance().isWallOneStepOnTheLeft(getBodyId());
    }

    public default boolean isWallOneStepOnTheRight() {
	return getPerception().getAppearance().isWallOneStepOnTheRight(getBodyId());
    }
    
    public default boolean doesLeftExist() {
	return !isWallLeft();
    }
    
    public default boolean doesRightExist() {
	return !isWallRight();
    }
    
    public default boolean doesForwardExist() {
	return !isWallForward();
    }

    public default boolean doesFrontLeftExist() {
	return !isWallForward() && !isWallLeft();
    }
    
    public default boolean doesFrontRightExist() {
	return !isWallForward() && !isWallRight();
    }

    /**
     * Is the location empty immediately forward?
     */
    public default boolean isEmptyForward() {
	return getPerception().getAppearance().isAheadEmpty(getBodyId());
    }

    /**
     * Is the location empty immediately left?
     */
    public default boolean isEmptyLeft() {
	return getPerception().getAppearance().isLeftEmpty(getBodyId());
    }

    /**
     * Is the location empty immediately right?
     */
    public default boolean isEmptyRight() {
	return getPerception().getAppearance().isRightEmpty(getBodyId());
    }

    /**
     * Is the location empty forward left?
     */
    public default boolean isEmptyForwardLeft() {
	return getPerception().getAppearance().isForwardLeftEmpty(getBodyId());
    }

    /**
     * Is the location empty forward right?
     */
    public default boolean isEmptyForwardRight() {
	return getPerception().getAppearance().isForwardRightEmpty(getBodyId());
    }
    
    public default boolean isAheadFreeFromActiveBodies() {
	VacuumWorldCoordinates coordinates = getCoordinatesForward();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isFreeFromActiveBodies();
    }
    
    public default boolean isLeftFreeFromActiveBodies() {
	VacuumWorldCoordinates coordinates = getCoordinatesLeft();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isFreeFromActiveBodies();
    }
    
    public default boolean isRightFreeFromActiveBodies() {
	VacuumWorldCoordinates coordinates = getCoordinatesRight();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isFreeFromActiveBodies();
    }
    
    public default boolean isForwardLeftFreeFromActiveBodies() {
	VacuumWorldCoordinates coordinates = getCoordinatesForwardLeft();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isFreeFromActiveBodies();
    }
    
    public default boolean isForwardRightFreeFromActiveBodies() {
	VacuumWorldCoordinates coordinates = getCoordinatesForwardRight();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isFreeFromActiveBodies();
    }
    
    /**
     * Is the current location dirty?
     */
    public default boolean isDirt() {
	return getPerception().getAppearance().isThereDirt(getBodyId());
    }

    /**
     * Is the location dirty immediately forward?
     */
    public default boolean isDirtForward() {
	return getPerception().getAppearance().isThereDirtAhead(getBodyId());
    }

    /**
     * Is the location dirty immediately left?
     */
    public default boolean isDirtLeft() {
	return getPerception().getAppearance().isThereDirtOnTheLeft(getBodyId());
    }

    /**
     * Is the location dirty immediately left?
     */
    public default boolean isDirtRight() {
	return getPerception().getAppearance().isThereDirtOnTheRight(getBodyId());
    }

    /**
     * Is the location dirty forward left?
     */
    public default boolean isDirtForwardLeft() {
	return getPerception().getAppearance().isThereDirtOnForwardLeft(getBodyId());
    }

    /**
     * Is the location dirty forward right?
     */
    public default boolean isDirtForwardRight() {
	return getPerception().getAppearance().isThereDirtOnForwardRight(getBodyId());
    }

    public default boolean isThereCompatibleDirtOnSelfLocation() {
	VacuumWorldCoordinates coordinates = getSelfCoordinates();
	AgentColor color = getSelfColorIfApplicable();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isCompatibleDirtThere(color);
    }
    
    public default boolean isThereCompatibleDirtAhead() {
	VacuumWorldCoordinates coordinates = getCoordinatesForward();
	AgentColor color = getSelfColorIfApplicable();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isCompatibleDirtThere(color);
    }
    
    public default boolean isThereCompatibleDirtOnTheLeft() {
	VacuumWorldCoordinates coordinates = getCoordinatesLeft();
	AgentColor color = getSelfColorIfApplicable();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isCompatibleDirtThere(color);
    }
    
    public default boolean isThereCompatibleDirtOnTheRight() {
	VacuumWorldCoordinates coordinates = getCoordinatesRight();
	AgentColor color = getSelfColorIfApplicable();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isCompatibleDirtThere(color);
    }
    
    public default boolean isThereCompatibleDirtForwardLeft() {
	VacuumWorldCoordinates coordinates = getCoordinatesForwardLeft();
	AgentColor color = getSelfColorIfApplicable();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isCompatibleDirtThere(color);
    }
    
    public default boolean isThereCompatibleDirtForwardRight() {
	VacuumWorldCoordinates coordinates = getCoordinatesForwardRight();
	AgentColor color = getSelfColorIfApplicable();
	
	return doesLocationExist(coordinates) && getGrid().get(coordinates).isCompatibleDirtThere(color);
    }    
    
    /**
     * Is there another agent at this agents immediate forward location?
     */
    public default boolean isAgentForward() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesForward()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents immediate left location?
     */
    public default boolean isAgentLeft() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesLeft()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents immediate right location?
     */
    public default boolean isAgentRight() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesRight()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents forward right location?
     */
    public default boolean isAgentForwardRight() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardRight()).isACleaningAgentThere();
    }

    /**
     * Is there another agent at this agents forward left location?
     */
    public default boolean isAgentForwardLeft() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardLeft()).isACleaningAgentThere();
    }

    /**
     * Is there a user at this agents immediate forward location?
     */
    public default boolean isUserForward() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesForward()).isAUserThere();
    }

    /**
     * Is there a user at this agents immediate left location?
     */
    public default boolean isUserLeft() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesLeft()).isAUserThere();
    }

    /**
     * Is there a user at this agents immediate right location?
     */
    public default boolean isUserRight() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesRight()).isAUserThere();
    }

    /**
     * Is there a user at this agents forward right location?
     */
    public default boolean isUserForwardRight() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardRight()).isAUserThere();
    }

    /**
     * Is there a user at this agents forward left location?
     */
    public default boolean isUserForwardLeft() {
	return getPerception().getAppearance().getGrid().get(this.getCoordinatesForwardLeft()).isAUserThere();
    }

    /**
     * Gets the current coordinates of this agent
     * 
     * @return the current coordinates of this agent
     */
    public default VacuumWorldCoordinates getSelfCoordinates() {
	return getPerception().getAppearance().getLocationFromActiveBodyId(getBodyId()).getCoordinates();
    }

    /**
     * Gets the current coordinates immediately left of this agent
     * 
     * @return the current coordinates immediately left of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesLeft() {
	return getSelfCoordinates().getLeftCoordinates(getPerception().getAppearance().getActorAppearance(getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates immediately right of this agent
     * 
     * @return the current coordinates immediately right of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesRight() {
	return getSelfCoordinates().getRightCoordinates(getPerception().getAppearance().getActorAppearance(getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates immediately forward of this agent
     * 
     * @return the current coordinates immediately forward of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesForward() {
	return getSelfCoordinates().getForwardCoordinates(getPerception().getAppearance().getActorAppearance(getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates forward left of this agent
     * 
     * @return the current coordinates forward left of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesForwardLeft() {
	return getSelfCoordinates().getForwardLeftCoordinates(getPerception().getAppearance().getActorAppearance(getBodyId()).getOrientation());
    }

    /**
     * Gets the current coordinates forward right of this agent
     * 
     * @return the current coordinates forward right of this agent
     */
    public default VacuumWorldCoordinates getCoordinatesForwardRight() {
	return getSelfCoordinates().getForwardRightCoordinates(getPerception().getAppearance().getActorAppearance(getBodyId()).getOrientation());
    }

    /**
     * Gets the current location of this agent
     * 
     * @return the current location of this agent
     */
    public default VacuumWorldLocationAppearance getSelfLocation() {
	return this.getGrid().get(getSelfCoordinates());
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
    public default VacuumWorldLocationAppearance getLocationForwardLeft() {
	return this.getGrid().get(getCoordinatesForwardLeft());
    }

    /**
     * Gets the current location forward right of this agent
     * 
     * @return the current location forward right of this agent
     */
    public default VacuumWorldLocationAppearance getLocationForwardRight() {
	return this.getGrid().get(getCoordinatesForwardRight());
    }
}