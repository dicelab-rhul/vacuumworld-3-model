package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/*
 * 
 * This is for part 2 of the course-work only.
 * 
 */
public class VacuumWorldCleanerMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 2175174131294190700L;
    //Add any class attribute you want/need.

    public VacuumWorldCleanerMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId);
	
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