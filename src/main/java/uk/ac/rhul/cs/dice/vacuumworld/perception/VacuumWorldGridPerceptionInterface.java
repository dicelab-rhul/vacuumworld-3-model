package uk.ac.rhul.cs.dice.vacuumworld.perception;

import java.util.Map;
import java.util.Set;

import uk.ac.rhul.cs.dice.agent.interfaces.ActiveBody;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
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
    
    public abstract Map<VacuumWorldCoordinates, VacuumWorldLocationAppearance> getGrid();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocations();
    
    public abstract Set<VacuumWorldCoordinates> getAllCoordinates();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgents();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgents();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgents();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgents();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithCleaningAgentsExcludingSelf(String selfId);
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithGreenAgentsExcludingSelf(String selfId);
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithOrangeAgentsExcludingSelf(String selfId);
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithWhiteAgentsExcludingSelf(String selfId);
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithUsers();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsWithAvatars();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllEmptyLocations();
    
    public abstract Set<VacuumWorldLocationAppearance> getAllLocationsFreeFromActiveBodies();
    
    public abstract VacuumWorldLocationAppearance getLocationFromActiveBodyId(String id);
    
    public abstract boolean isWallJustAhead(String id);
    
    public abstract boolean isWallJustOnTheLeft(String id);
    
    public abstract boolean isWallJustOnTheRight(String id);
    
    public abstract boolean isWallJustBehind(String id);
    
    public abstract boolean isWallOneStepAhead(String id);
    
    public abstract boolean isWallOneStepOnTheLeft(String id);
    
    public abstract boolean isWallOneStepOnTheRight(String id);
    
    public abstract boolean isAheadEmpty(String id);
    
    public abstract boolean isLeftEmpty(String id);
    
    public abstract boolean isRightEmpty(String id);
    
    public abstract boolean isForwardLeftEmpty(String id);
    
    public abstract boolean isForwardRightEmpty(String id);
    
    public abstract boolean isAheadFreeFromActiveBodies(String id);
    
    public abstract boolean isLeftFreeFromActiveBodies(String id);
    
    public abstract boolean isRightFreeFromActiveBodies(String id);
    
    public abstract boolean isForwardLeftFreeFromActiveBodies(String id);
    
    public abstract boolean isForwardRightFreeFromActiveBodies(String id);
	
    public abstract boolean isThereDirt(String id);
    
    public abstract boolean isThereDirtAhead(String id);
    
    public abstract boolean isThereDirtOnTheLeft(String id);
    
    public abstract boolean isThereDirtOnTheRight(String id);
    
    public abstract boolean isThereDirtOnForwardLeft(String id);
    
    public abstract boolean isThereDirtOnForwardRight(String id);
    
    public abstract AgentColor getAgentColorIfApplicable(String id);
    
    public abstract boolean isThereCompatibleDirt(String id);
    
    public abstract boolean isThereCompatibleDirtAhead(String id);
    
    public abstract boolean isThereCompatibleDirtOnTheLeft(String id);
    
    public abstract boolean isThereCompatibleDirtOnTheRight(String id);
    
    public abstract boolean isThereCompatibleDirtOnForwardLeft(String id);
    
    public abstract boolean isThereCompatibleDirtOnForwardRight(String id);
}