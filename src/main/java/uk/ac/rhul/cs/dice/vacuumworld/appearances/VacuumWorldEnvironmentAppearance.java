package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.ac.rhul.cs.dice.agentcontainers.interfaces.EnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldCoordinates;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocationAppearance;

public class VacuumWorldEnvironmentAppearance implements EnvironmentAppearance {
    private static final long serialVersionUID = 3587669408864383174L;
    private Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> grid;
    
    public VacuumWorldEnvironmentAppearance(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid) {
	Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> appearancesGrid = new HashMap<>();
	grid.entrySet().forEach(e -> appearancesGrid.put(e.getKey(), e.getValue().getAppearance()));
	
	this.grid = ImmutableMap.copyOf(appearancesGrid);
    }
    
    public Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> getGrid() {
	return this.grid;
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocations() {
	return ImmutableSet.copyOf(this.grid.values());
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isACleaningAgentThere).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAGreenAgentThere).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnOrangeAgentThere).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgents() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAWhiteAgentThere).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isACleaningAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAGreenAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnOrangeAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgentsExcludingSelf(String selfId) {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAWhiteAgentThere).filter(loc -> !loc.getAgentAppearanceIfAny().getId().equals(selfId)).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithUsers() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAUserThere).collect(Collectors.toSet()));
    }
    
    public Set<VacuumWorldLocationAppearance> getAllLocationsWithAvatars() {
	return ImmutableSet.copyOf(this.grid.values().stream().filter(VacuumWorldLocationAppearance::isAnAvatarThere).collect(Collectors.toSet()));
    }
    
    public boolean isWallJustAhead(String selfId) {
	//TODO
	return false;
    }
    
    public boolean isWallOneStepAhead(String selfId) {
	//TODO
	return false;
    }
    
    public boolean isWallJustOnTheLeft(String selfId) {
	//TODO
	return false;
    }
    
    public boolean isWallJustOnTheRight(String selfId) {
	//TODO
	return false;
    }
    
    public boolean isWallOneStepOnTheLeft(String selfId) {
	//TODO
	return false;
    }
    
    public boolean isWallOneStepOnTheRight(String selfId) {
	//TODO
	return false;
    }
    
    public boolean canMoveAhead(String selfId) {
	//TODO
	return false;
    }
    
    //TODO create the full API for the perception
}