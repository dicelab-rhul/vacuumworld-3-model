package uk.ac.rhul.cs.dice.vacuumworld.actors;

import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Avatar;
import uk.ac.rhul.cs.dice.agent.interfaces.PrincipalListener;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.TurnDirection;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public interface VacuumWorldActor extends Actor, Avatar {
    
    @Override
    public default void sendFeedbackToPrincipal(Analyzable... feedback) {
        throw new UnsupportedOperationException("Not an Avatar!");
    }
    
    @Override
    public default PrincipalListener getPrincipalListener() {
	throw new UnsupportedOperationException("Not an Avatar!");
    }
    
    public default void turn(TurnDirection direction) {
	switch(direction) {
	case LEFT:
	    turnLeft();
	    break;
	case RIGHT:
	    turnRight();
	    break;
	default:
	    throw new IllegalArgumentException();
	}
    }
    
    public abstract VacuumWorldActorAppearance getAppearance();
    
    public abstract void setStopFlag(boolean stop);
    
    public abstract void setPauseFlag(boolean pause);
    
    public abstract void toggleTest();
}