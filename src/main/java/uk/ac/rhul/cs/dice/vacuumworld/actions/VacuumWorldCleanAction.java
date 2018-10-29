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

public class VacuumWorldCleanAction extends VacuumWorldAbstractPhysicalAction {
    private static final long serialVersionUID = -570777551545856621L;
    private VacuumWorldDirtColor cleanedDirtColor;

    public VacuumWorldCleanAction() {
	super(VacuumWorldPhysicalActionsEnum.CLEAN);
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldPhysicalActionsEnum.CLEAN.equals(getType());
    }

    public void setCleanedDirtColor(VacuumWorldDirtColor cleanedDirtColor) {
	this.cleanedDirtColor = cleanedDirtColor;
    }
    
    public VacuumWorldDirtColor getCleanedDirtColor() {
	return this.cleanedDirtColor;
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
        return "C";
    }
}