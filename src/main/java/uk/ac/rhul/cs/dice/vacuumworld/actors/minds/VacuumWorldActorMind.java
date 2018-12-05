package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

public interface VacuumWorldActorMind extends VacuumWorldPerceptiveEntity, AgentMind {
    
    public default int getIntRngLowerLimit() {
        return getRngLowerLimit().intValueExact();
    }
    
    public default int getIntRngUpperLimit() {
	 return getRngUpperLimit().intValueExact();
    }
    
    public abstract VacuumWorldAbstractAction decideWithRNG();
}