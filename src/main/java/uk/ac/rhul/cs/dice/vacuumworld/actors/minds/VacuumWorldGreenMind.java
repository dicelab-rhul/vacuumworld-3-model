package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/*
 * 
 * This is for part 3 of the course-work only.
 * This class is for green agents.
 * 
 */
public class VacuumWorldGreenMind extends VacuumWorldColoredMind {
    private static final long serialVersionUID = -3848363346194146181L;
    //Add any class attribute you want/need.

    public VacuumWorldGreenMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId); //Add any additional parameter you need, if the superclass constructor requires more.
	
	//Edit here if needed.
    }

    @Override
    public VacuumWorldAbstractAction decide() {
	//Edit here.
	
	//HINT: always call hasPerception() in order to check whether the perception exists.
	//HINT #2: call getPerception() in order to get the perception.
	
	//Change this with the desired behavior. Remember: NEVER return null!
	return super.decideRandomly();
    }
    
    //Add any method you need.
}