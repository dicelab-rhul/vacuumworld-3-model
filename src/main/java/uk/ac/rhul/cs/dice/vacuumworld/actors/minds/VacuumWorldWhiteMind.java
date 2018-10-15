package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/*
 * 
 * This is for part 3 of the course-work only.
 * This class is for white agents.
 * 
 */
public class VacuumWorldWhiteMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = 2760454725661174370L;
    //Add any class attribute you want/need.

    public VacuumWorldWhiteMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId);
	
	//Edit here if needed.
    }
    
    @Override
    public VacuumWorldAbstractAction decide() {
	//Edit here.
	
	//Change this with the desired behavior. Remember: NEVER return null!
	return super.decideRandomly();
    }

    @Override
    public void revise() {
	//Edit here.
    }
    
    //Add any method you need.
}