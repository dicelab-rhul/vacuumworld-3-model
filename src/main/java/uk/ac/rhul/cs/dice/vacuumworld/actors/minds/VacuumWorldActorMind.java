package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/**
 * 
 * This interface exposes RNG-related utility methods for actor minds.<br/><br/>
 * 
 * Extends {@link VacuumWorldPerceptiveEntity} and {@link AgentMind}.<br/><br/>
 * 
 * Known implementations: {@link VacuumWorldAbstractMind}.
 * 
 * @author cloudstrife9999
 *
 */
public interface VacuumWorldActorMind extends VacuumWorldPerceptiveEntity, AgentMind {
    
    /**
     * 
     * Returns the lower limit (inclusive) of the possible output of the RNG.
     * 
     * @return the lower limit (inclusive) of the possible output of the RNG.
     * 
     */
    public default int getIntRngLowerLimit() {
        return getRngLowerLimit().intValueExact();
    }
    
    /**
     * 
     * Returns the uppper limit (inclusive) of the possible output of the RNG.
     * 
     * @return the upper limit (inclusive) of the possible output of the RNG.
     * 
     */
    public default int getIntRngUpperLimit() {
	 return getRngUpperLimit().intValueExact();
    }
    
    /**
     * 
     * Returns an action based on a RNG roll.
     * 
     * @return the decided action.
     * 
     */
    public abstract VacuumWorldAbstractAction decideWithRNG();
}