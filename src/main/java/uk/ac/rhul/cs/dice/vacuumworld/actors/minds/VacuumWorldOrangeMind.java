package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/*
 * 
 * This is for part 3 of the course-work only.
 * This class is for orange agents.
 * 
 */
public class VacuumWorldOrangeMind extends VacuumWorldColoredMind {
    private static final long serialVersionUID = 3924215916983146282L;
    //Add any class attribute you want/need.

    public VacuumWorldOrangeMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId); //Add any additional parameter you need, if the superclass constructor requires more.
	
	//Edit here if needed.
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	//Edit here.
	
	//Change this with the desired behavior. Remember: NEVER return null!
	return super.decideRandomly();
    }
    
    //Add any method you need.
}