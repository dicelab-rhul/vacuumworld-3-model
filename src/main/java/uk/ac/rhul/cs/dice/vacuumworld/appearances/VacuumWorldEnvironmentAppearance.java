package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

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
    
    //TODO create the full API for the perception
}