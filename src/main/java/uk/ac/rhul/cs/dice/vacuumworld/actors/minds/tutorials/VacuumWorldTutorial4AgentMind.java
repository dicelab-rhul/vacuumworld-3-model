package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.tutorials;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

public class VacuumWorldTutorial4AgentMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = -3008424231880872807L;
    //Add any class attribute you want/need.

    public VacuumWorldTutorial4AgentMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId);
	
	//Edit here if needed.
    }

    @Override
    public void revise() {
	//Edit here.
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	//Edit here.
	
	//Change this with the desired behavior. Remember: NEVER return null!
	return null;
    }

    //Add any method you need.
}