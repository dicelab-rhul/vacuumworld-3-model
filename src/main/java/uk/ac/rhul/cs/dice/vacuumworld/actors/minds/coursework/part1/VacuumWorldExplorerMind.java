package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.coursework.part1;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

/*
 * 
 * This is for part 1 of the course-work only.
 * Feel free to change the superclass, if needed.
 * Do not subclass this class.
 * 
 */
public class VacuumWorldExplorerMind extends VacuumWorldAbstractMind {
    private static final long serialVersionUID = -7230221324735131629L;
    //Add any class attribute you want/need.

    public VacuumWorldExplorerMind(String bodyId) { //Add any additional parameter you need.
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