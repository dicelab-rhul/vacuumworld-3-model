package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.physics.VacuumWorldPhysicsInterface;

public class VacuumWorldDropDirtAction extends VacuumWorldAbstractPhysicalAction {
    private static final long serialVersionUID = 2037182628265165811L;
    private VacuumWorldDirtColor droppedDirtColor;

    public VacuumWorldDropDirtAction(VacuumWorldDirtColor droppedDirtColor) {
	super(VacuumWorldPhysicalActionsEnum.DROP_DIRT);
	
	this.droppedDirtColor = droppedDirtColor == null ? selectRandomDirtColor() : droppedDirtColor;
    }

    private VacuumWorldDirtColor selectRandomDirtColor() {
	return VacuumWorldDirtColor.random();
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldPhysicalActionsEnum.DROP_DIRT.equals(getType());
    }
    
    public VacuumWorldDirtColor getDroppedDirtColor() {
	return this.droppedDirtColor;
    }

    @Override
    public boolean isPossible(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).isPossible(this, (VacuumWorldEnvironment) context);
        }
        
        return false;
    }

    @Override
    public Result perform(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).perform(this, (VacuumWorldEnvironment) context);
        }
        
        return new VacuumWorldPhysicalActionResult(ActionResult.FAILURE);
    }
    
    @Override
    public boolean succeeded(Environment context, Physics physics) {
        if(context instanceof VacuumWorldEnvironment && physics instanceof VacuumWorldPhysicsInterface) {
            return ((VacuumWorldPhysicsInterface) physics).succeeded(this, (VacuumWorldEnvironment) context);
        }
        
        return false;
    }
    
    @Override
    public String toShortString() {
        return "D";
    }
}