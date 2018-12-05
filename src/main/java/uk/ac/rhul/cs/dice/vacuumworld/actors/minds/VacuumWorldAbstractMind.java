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
import uk.ac.rhul.cs.dice.vacuumworld.perception.NothingMoreIncomingPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

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

    @Override
    public abstract VacuumWorldAbstractAction decide();

    @Override
    public void perceive(Set<Analyzable> perceptions) {
	super.perceive(null);

	this.lastCyclePerceptions.clear();
	this.lastCycleReceivedMessages.clear();

	perceptions.forEach(this::dealWithPercept);
    }
    
    @Override
    public void receiveFirstPerception(Analyzable perception) {
        perceive(new HashSet<>(Arrays.asList(perception)));
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

    @Override
    public final <T extends Action<?>> void execute(T action) {
	((VacuumWorldAbstractAction) action).setActor(getBodyId());
	LogUtils.log(action.getActorID() + " is executing " + action.getClass().getSimpleName());
    }
}