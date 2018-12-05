package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldCleanAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.perception.NothingMoreIncomingPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

public abstract class VacuumWorldAbstractMind extends AbstractAgentMind implements VacuumWorldActorMind {
    private static final long serialVersionUID = 5415182091402486290L;
    private static final int DEFAULT_RNG_LOWER_LIMIT = 0;
    private static final int DEFAULT_RNG_UPPER_LIMIT = 3;
    private List<VacuumWorldPerception> lastCyclePerceptions;
    private List<VacuumWorldSpeechPerception> lastCycleReceivedMessages;
    private final int rngLowerLimit;
    private final int rngUpperLimit;

    public VacuumWorldAbstractMind(String bodyId) {
	this(bodyId, VacuumWorldAbstractMind.DEFAULT_RNG_LOWER_LIMIT, VacuumWorldAbstractMind.DEFAULT_RNG_UPPER_LIMIT);
    }
    
    public VacuumWorldAbstractMind(String bodyId, int rngLowerLimit, int rngUpperLimit) {
	super(bodyId);

	this.lastCyclePerceptions = new ArrayList<>();
	this.lastCycleReceivedMessages = new ArrayList<>();
	this.rngLowerLimit = rngLowerLimit <= rngUpperLimit ? rngLowerLimit : VacuumWorldAbstractMind.DEFAULT_RNG_LOWER_LIMIT;
	this.rngUpperLimit = rngUpperLimit >= rngLowerLimit ? rngUpperLimit : VacuumWorldAbstractMind.DEFAULT_RNG_UPPER_LIMIT;
    }

    @Override
    public BigDecimal getRngLowerLimit() {
        return BigDecimal.valueOf(this.rngLowerLimit);
    }
    
    @Override
    public BigDecimal getRngUpperLimit() {
        return BigDecimal.valueOf(this.rngUpperLimit);
    }
    
    @Override
    public VacuumWorldPerception getPerception() {
	return this.lastCyclePerceptions.isEmpty() ? null : this.lastCyclePerceptions.get(0);
    }

    @Override
    public boolean hasNewMessages() {
        return !this.lastCycleReceivedMessages.isEmpty();
    }
    
    @Override
    public List<VacuumWorldSpeechPerception> getMessages() {
	return this.lastCycleReceivedMessages;
    }

    /**
     * 
     * This method is always automatically called at the beginning of each cycle (except for the first cycle) before {@link #revise()}.<br/><br/>
     * 
     * Its intended practical use is to fetch an updated {@link VacuumWorldPerception} from the {@link VacuumWorldEnvironment},
     * as well as any {@link VacuumWorldMessage} received during the latest cycle.<br/><br/>
     * 
     * The {@link VacuumWorldPerception} is always the first {@link Analyzable} of the {@link Set} passed as parameter.
     * Any following element is a {@link VacuumWorldMessage}.<br/><br/>
     * 
     * Both the perception, and the messages are stored in internal {@link List}s.
     * The perception and the messages from the cycle(s) before the latest are deleted in the process.
     * 
     */
    @Override
    public void perceive(Set<Analyzable> perceptions) {
	super.perceive(null);

	this.lastCyclePerceptions.clear();
	this.lastCycleReceivedMessages.clear();

	perceptions.forEach(this::dealWithPercept);
    }
    
    /**
     * 
     * This method is always automatically called at the beginning of the first cycle before {@link #revise()}.<br/><br/>
     * 
     * Its intended practical use is to fetch the initial {@link VacuumWorldPerception} from the {@link VacuumWorldEnvironment}.
     * 
     */
    @Override
    public void receiveFirstPerception(Analyzable perception) {
        perceive(new HashSet<>(Arrays.asList(perception)));
    }
    
    /**
     * 
     * This method is always automatically called after {@link #revise()}, and before {@link #execute()}.<br/><br/>
     * 
     * Its intended use is to output the next action for the agent to {@link #execute()}.<br/><br/>
     * 
     */
    @Override
    public abstract VacuumWorldAbstractAction decide();

    /**
     * 
     * Returns an action based on a RNG roll.<br/><br/>
     * 
     * rngValue == rngLowerLimit -> {@link VacuumWorldMoveAction}.<br/>
     * rngValue == rngLowerLimit + 1 -> {@link VacuumWorldTurnLeftAction}.<br/>
     * rngValue == rngLowerLimit + 2 -> {@link VacuumWorldTurnRightAction}.<br/>
     * rngValue == rngLowerLimit + 3 -> {@link VacuumWorldCleanAction}.<br/>
     * else -> {@link VacuumWorldIdleAction}.<br/>
     * 
     * @return a random {@link VacuumWorldAbstractAction}.
     * 
     */
    @Override
    public VacuumWorldAbstractAction decideWithRNG() {
	final int rngValue = getRng().nextInt(getIntRngUpperLimit() + 1) + getIntRngLowerLimit();
	
	if(rngValue == getIntRngLowerLimit()) {
	    return new VacuumWorldMoveAction();
	}
	else if(rngValue == getIntRngLowerLimit() + 1) {
	    return new VacuumWorldTurnLeftAction();
	}
        else if(rngValue == getIntRngLowerLimit() + 2) {
            return new VacuumWorldTurnRightAction();
        }
        else if(rngValue == getIntRngLowerLimit() + 3) {
            return new VacuumWorldCleanAction();
        }
        else {
            return new VacuumWorldIdleAction();
        }
    }

    /**
     * 
     * This method is always automatically called after {@link #decide()}.<br/><br/>
     * 
     * Its intended use is to prepare to execute the action returned by {@link #decide()}.
     * 
     */
    @Override
    public final <T extends Action<?>> void execute(T action) {
	try {
	    ((VacuumWorldAbstractAction) action).setActor(getBodyId());
	    LogUtils.log(action.getActorID() + " is executing " + action.getClass().getSimpleName());
	}
	catch(ClassCastException e) {
	    throw new VacuumWorldRuntimeException(action.getClass().getName() + "is not castable to " + VacuumWorldAbstractAction.class.getName() + "!", e);
	}
    }
    
    private void dealWithPercept(Analyzable a) {
	if (VacuumWorldPerception.class.isAssignableFrom(a.getClass())) {
	    this.lastCyclePerceptions.add((VacuumWorldPerception) a);
	}
	else if (VacuumWorldSpeechPerception.class.isAssignableFrom(a.getClass())) {
	    this.lastCycleReceivedMessages.add((VacuumWorldSpeechPerception) a);
	}
	else if (!NothingMoreIncomingPerception.class.isAssignableFrom(a.getClass())) {
	    getDefaultLastReceivedPerceptions().add(a);
	}
    }
}