package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

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
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public abstract class VacuumWorldAbstractMind extends AbstractAgentMind implements VacuumWorldPerceptiveEntity {
    private static final long serialVersionUID = 5415182091402486290L;
    private List<VacuumWorldPerception> lastCyclePerceptions;
    private List<VacuumWorldSpeechPerception> lastCycleReceivedMessages;

    public VacuumWorldAbstractMind(String bodyId) {
	super(bodyId);

	this.lastCyclePerceptions = new ArrayList<>();
	this.lastCycleReceivedMessages = new ArrayList<>();
    }

    @Override
    public VacuumWorldPerception getPerception() {
	return this.lastCyclePerceptions.isEmpty() ? null : this.lastCyclePerceptions.get(0);
    }

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
	else {
	    getDefaultLastReceivedPerceptions().add(a);
	}
    }

    public VacuumWorldAbstractAction decideRandomly() {
	switch (getRng().nextInt(4)) {
	case 0:
	    return new VacuumWorldMoveAction();
	case 1:
	    return new VacuumWorldTurnLeftAction();
	case 2:
	    return new VacuumWorldTurnRightAction();
	case 3:
	    return new VacuumWorldCleanAction();
	default:
	    return new VacuumWorldIdleAction();
	}
    }

    @Override
    public final <T extends Action<?>> void execute(T action) {
	((VacuumWorldAbstractAction) action).setActor(getBodyId());
	LogUtils.log(action.getActorID() + " is executing " + action.getClass().getSimpleName());
    }
}