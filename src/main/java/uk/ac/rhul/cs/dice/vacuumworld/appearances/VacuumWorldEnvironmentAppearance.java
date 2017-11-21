package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldGridPerceptionInterface;

/**
 * 
 * Implementation of {@link VacuumWorldGridPerceptionInterface} and {@link EnvironmentAppearance}.
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldEnvironmentAppearance implements EnvironmentAppearance, VacuumWorldGridPerceptionInterface {
    private static final long serialVersionUID = 3587669408864383174L;
    private Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> grid;
    
    /**
     * 
     * Constructs a {@link VacuumWorldEnvironmentAppearance} from a {@link Map} from {@link VacuumWorldCoordinates} to {@link VacuumWorldLocation} representing the grid.
     * 
     * @param grid a {@link Map} from {@link VacuumWorldCoordinates} to {@link VacuumWorldLocation} representing the grid.
     * 
     */
    public VacuumWorldEnvironmentAppearance(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid) {
	Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> appearancesGrid = new HashMap<>();
	grid.entrySet().forEach(e -> appearancesGrid.put(e.getKey(), e.getValue().getAppearance()));
	
	this.grid = ImmutableMap.copyOf(appearancesGrid);
    }
    
    @Override
    public Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> getGrid() {
	return this.grid;
    }
    
    @Override
    public int countNumberOfLocations() {
	return this.grid.size();
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocations() {
	return ImmutableSet.copyOf(this.grid.values());
    }
    
    @Override
    public Set<VacuumWorldCoordinates> getAllCoordinates() {
	return ImmutableSet.copyOf(this.grid.keySet());
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isACleaningAgentThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAGreenAgentThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnOrangeAgentThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAWhiteAgentThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isACleaningAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAGreenAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnOrangeAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAWhiteAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithUsers() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAUserThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithAvatars() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnAvatarThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithActiveBodies() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnActiveBodyThere).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllEmptyLocations() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isEmpty).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<VacuumWorldLocationAppearance> getAllLocationsFreeFromActiveBodies() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isFreeFromActiveBodies).collect(Collectors.toSet()));
    }
    
    @Override
    public VacuumWorldLocationAppearance getLocationFromActiveBodyId(String id) {
	return this.grid.values().stream().filter(location -> location.containsSuchActiveBody(id)).findFirst().orElse(null);
    }
    
    @Override
    public boolean isWallOneStepAhead(String id) {
	VacuumWorldLocationAppearance location = getLocationFromActiveBodyId(id);
	Orientation orientation = location.getActiveBodyOrientation();
	VacuumWorldCoordinates ahead = (VacuumWorldCoordinates) location.getCoordinates().getForwardCoordinates(orientation);
	
	return doesSuchLocationExistInPerception(ahead) && this.grid.get(ahead).checkForWall(orientation);
    }
    
    @Override
    public boolean isWallOneStepOnTheLeft(String id) {
	VacuumWorldLocationAppearance location = getLocationFromActiveBodyId(id);
	Orientation orientation = location.getActiveBodyOrientation();
	VacuumWorldCoordinates left = (VacuumWorldCoordinates) location.getCoordinates().getLeftCoordinates(orientation);
	
	return doesSuchLocationExistInPerception(left) && this.grid.get(left).checkForWall(orientation.getLeft());
    }
    
    @Override
    public boolean isWallOneStepOnTheRight(String id) {
	VacuumWorldLocationAppearance location = getLocationFromActiveBodyId(id);
	Orientation orientation = location.getActiveBodyOrientation();
	VacuumWorldCoordinates right = (VacuumWorldCoordinates) location.getCoordinates().getRightCoordinates(orientation);
	
	return doesSuchLocationExistInPerception(right) && this.grid.get(right).checkForWall(orientation.getRight());
    }
    
    private boolean doesSuchLocationExistInPerception(VacuumWorldCoordinates coordinates) {
	return this.grid.containsKey(coordinates);
    }
    
    @Override
    public boolean isAheadEmpty(String id) {
	VacuumWorldCoordinates coordinates = getForwardCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isEmpty();
    }
    
    @Override
    public boolean isLeftEmpty(String id) {
	VacuumWorldCoordinates coordinates = getLeftCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isEmpty();
    }
    
    @Override
    public boolean isRightEmpty(String id) {
	VacuumWorldCoordinates coordinates = getRightCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isEmpty();
    }
    
    @Override
    public boolean isForwardLeftEmpty(String id) {
	VacuumWorldCoordinates coordinates = getForwardLeftCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isEmpty();
    }
    
    @Override
    public boolean isForwardRightEmpty(String id) {
	VacuumWorldCoordinates coordinates = getForwardRightCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isEmpty();
    }
    
    private Orientation getActiveBodyOrientation(String id) {
	VacuumWorldLocationAppearance location = getLocationFromActiveBodyId(id);
	
	return  location.getActiveBodyOrientation();
    }
    
    private VacuumWorldCoordinates getForwardCoordinates(String id) {
	return (VacuumWorldCoordinates) getLocationFromActiveBodyId(id).getCoordinates().getForwardCoordinates(getActiveBodyOrientation(id));
    }
    
    private VacuumWorldCoordinates getLeftCoordinates(String id) {
	return (VacuumWorldCoordinates) getLocationFromActiveBodyId(id).getCoordinates().getLeftCoordinates(getActiveBodyOrientation(id));
    }
    
    private VacuumWorldCoordinates getRightCoordinates(String id) {
	return (VacuumWorldCoordinates) getLocationFromActiveBodyId(id).getCoordinates().getRightCoordinates(getActiveBodyOrientation(id));
    }
    
    private VacuumWorldCoordinates getForwardLeftCoordinates(String id) {
	return (VacuumWorldCoordinates) getLocationFromActiveBodyId(id).getCoordinates().getForwardLeftCoordinates(getActiveBodyOrientation(id));
    }
    
    private VacuumWorldCoordinates getForwardRightCoordinates(String id) {
	return (VacuumWorldCoordinates) getLocationFromActiveBodyId(id).getCoordinates().getForwardRightCoordinates(getActiveBodyOrientation(id));
    }
    
    @Override
    public boolean isAheadFreeFromActiveBodies(String id) {
	VacuumWorldCoordinates coordinates = getForwardCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isFreeFromActiveBodies();
    }
    
    @Override
    public boolean isLeftFreeFromActiveBodies(String id) {
	VacuumWorldCoordinates coordinates = getLeftCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isFreeFromActiveBodies();
    }
    
    @Override
    public boolean isRightFreeFromActiveBodies(String id) {
	VacuumWorldCoordinates coordinates = getRightCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isFreeFromActiveBodies();
    }
    
    @Override
    public boolean isForwardLeftFreeFromActiveBodies(String id) {
	VacuumWorldCoordinates coordinates = getForwardLeftCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isFreeFromActiveBodies();
    }
    
    @Override
    public boolean isForwardRightFreeFromActiveBodies(String id) {
	VacuumWorldCoordinates coordinates = getForwardRightCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isFreeFromActiveBodies();
    }
    
    @Override
    public boolean isThereDirt(String id) {
	VacuumWorldCoordinates coordinates = getLocationFromActiveBodyId(id).getCoordinates();
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isDirtThere();
    }
    
    @Override
    public boolean isThereDirtAhead(String id) {
	VacuumWorldCoordinates coordinates = getForwardCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isDirtThere();
    }
    
    @Override
    public boolean isThereDirtOnTheLeft(String id) {
	VacuumWorldCoordinates coordinates = getLeftCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isDirtThere();
    }
    
    @Override
    public boolean isThereDirtOnTheRight(String id) {
	VacuumWorldCoordinates coordinates = getRightCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isDirtThere();
    }
    
    @Override
    public boolean isThereDirtOnForwardLeft(String id) {
	VacuumWorldCoordinates coordinates = getForwardLeftCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isDirtThere();
    }
    
    @Override
    public boolean isThereDirtOnForwardRight(String id) {
	VacuumWorldCoordinates coordinates = getForwardRightCoordinates(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isDirtThere();
    }
    
    @Override
    public boolean isThereCompatibleDirt(String id) {
	VacuumWorldCoordinates coordinates = getLocationFromActiveBodyId(id).getCoordinates();
	AgentColor color = getAgentColorIfApplicable(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isCompatibleDirtThere(color);
    }
    
    @Override
    public boolean isThereCompatibleDirtAhead(String id) {
	VacuumWorldCoordinates coordinates = getForwardCoordinates(id);
	AgentColor color = getAgentColorIfApplicable(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isCompatibleDirtThere(color);
    }
    
    @Override
    public boolean isThereCompatibleDirtOnTheLeft(String id) {
	VacuumWorldCoordinates coordinates = getLeftCoordinates(id);
	AgentColor color = getAgentColorIfApplicable(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isCompatibleDirtThere(color);
    }
    
    @Override
    public boolean isThereCompatibleDirtOnTheRight(String id) {
	VacuumWorldCoordinates coordinates = getRightCoordinates(id);
	AgentColor color = getAgentColorIfApplicable(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isCompatibleDirtThere(color);
    }
    
    @Override
    public boolean isThereCompatibleDirtOnForwardLeft(String id) {
	VacuumWorldCoordinates coordinates = getForwardLeftCoordinates(id);
	AgentColor color = getAgentColorIfApplicable(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isCompatibleDirtThere(color);
    }
    
    @Override
    public boolean isThereCompatibleDirtOnForwardRight(String id) {
	VacuumWorldCoordinates coordinates = getForwardRightCoordinates(id);
	AgentColor color = getAgentColorIfApplicable(id);
	
	return doesSuchLocationExistInPerception(coordinates) && this.grid.get(coordinates).isCompatibleDirtThere(color);
    }
}