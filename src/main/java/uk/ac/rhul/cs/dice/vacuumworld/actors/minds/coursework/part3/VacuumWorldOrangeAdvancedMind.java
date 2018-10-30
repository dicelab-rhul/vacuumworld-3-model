package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.coursework.part3;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;

/*
 * 
 * This is for part 3 of the course-work only.
 * This class is for orange agents.
 * Feel free to change the superclass, if needed.
 * Do not subclass this class.
 * 
 */
public class VacuumWorldOrangeAdvancedMind extends VacuumWorldColoredAdvancedMind {
    private static final long serialVersionUID = 3924215916983146282L;
    //Add any class attribute you want/need.

    public VacuumWorldOrangeAdvancedMind(String bodyId) { //Add any additional parameter you need.
	super(bodyId); //Add any additional parameter you need, if the superclass constructor requires more.
	
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