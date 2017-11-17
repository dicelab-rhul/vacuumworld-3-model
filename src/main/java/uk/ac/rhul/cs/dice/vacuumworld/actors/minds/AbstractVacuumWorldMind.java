package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldMoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnLeftAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldTurnRightAction;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public abstract class AbstractVacuumWorldMind extends AbstractAgentMind {
    private static final long serialVersionUID = 5415182091402486290L;
    private List<VacuumWorldPerception> lastCyclePerceptions;
    private List<VacuumWorldSpeechPerception> lastCycleReceivedMessages;
    
    public AbstractVacuumWorldMind() {
	super();
	
	this.lastCyclePerceptions = new ArrayList<>();
	this.lastCycleReceivedMessages = new ArrayList<>();
    }

    @Override
    public void perceive(Set<Analyzable> perceptions) {
	super.perceive(null);
	
	this.lastCyclePerceptions.clear();
	this.lastCycleReceivedMessages.clear();
	
	perceptions.forEach(this::dealWithPercept);
    }

    private void dealWithPercept(Analyzable a) {
	if(VacuumWorldPerception.class.isAssignableFrom(a.getClass())) {
	    this.lastCyclePerceptions.add((VacuumWorldPerception) a);
	}
	else if(VacuumWorldSpeechPerception.class.isAssignableFrom(a.getClass())) {
	    this.lastCycleReceivedMessages.add((VacuumWorldSpeechPerception) a);
	}
	else {
	    getDefaultLastReceivedPerceptions().add(a);
	}
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	return new VacuumWorldSensingAction();
    }
    
    public VacuumWorldAbstractAction decideRandomly() {
	switch(getRng().nextInt(3)) {
	case 0:
	    return new VacuumWorldMoveAction();
	case 1:
	    return new VacuumWorldTurnLeftAction();
	case 2:
	    return new VacuumWorldTurnRightAction();
	default:
	    return this.decide();
	}
    }

    @Override
    public <T extends Action<?>> void execute(T action) {
	((VacuumWorldAbstractAction) action).setActor("Pippo"); //TODO remove this
	LogUtils.log(action.getActorID() + " is executing " + action.getClass().getSimpleName());
	
	return;
    }
}