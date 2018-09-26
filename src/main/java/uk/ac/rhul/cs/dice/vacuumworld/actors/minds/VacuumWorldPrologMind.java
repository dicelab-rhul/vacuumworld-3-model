package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.util.ArrayList;
import java.util.List;

import alice.tuprolog.Prolog;
import alice.tuprolog.Theory;
import uk.ac.rhul.cs.dice.agent.interfaces.PrologMind;

public class VacuumWorldPrologMind extends VacuumWorldAbstractMind implements PrologMind {
	private static final long serialVersionUID = -6107803494980916586L;
	private Prolog interpreter;
	private List<Theory> theories;
	
	public VacuumWorldPrologMind(String bodyId, Prolog interpreter) {
		super(bodyId);
		
		this.theories = new ArrayList<>();
		setInterpreter(interpreter);
	}

	@Override
	public Prolog getInterpreter() {
		return this.interpreter;
	}

	@Override
	public void setInterpreter(Prolog interpreter) {
		this.interpreter = interpreter;
	}

	@Override
	public List<Theory> getTheories() {
		return this.theories;
	}

	@Override
	public void setTheories(List<Theory> theories) {
		this.theories = theories;
	}
	
	@Override
	public Theory getFirstTheory() {		
		return this.theories != null ? PrologMind.super.getFirstTheory() : null;
	}

	@Override
	public Object decide() {
	    // TODO Auto-generated method stub
	    return null;
	}
}