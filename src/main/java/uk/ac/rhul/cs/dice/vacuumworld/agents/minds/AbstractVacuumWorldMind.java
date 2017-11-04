package uk.ac.rhul.cs.dice.vacuumworld.agents.minds;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public abstract class AbstractVacuumWorldMind extends AbstractAgentMind {
    private List<VacuumWorldPerception> lastCyclePerceptions;
    private List<VacuumWorldSpeechPerception> lastCycleReceivedMessages;
    
    public AbstractVacuumWorldMind() {
	super();
	
	this.lastCyclePerceptions = new ArrayList<>();
	this.lastCycleReceivedMessages = new ArrayList<>();
    }

    @Override
    public void perceive(List<Analyzable> perceptions) {
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
    public VacuumWorldSensingAction decide() {
	return new VacuumWorldSensingAction();
    }

    @Override
    public <T extends Action<?>> void execute(T action) {
	//TODO
    }
}