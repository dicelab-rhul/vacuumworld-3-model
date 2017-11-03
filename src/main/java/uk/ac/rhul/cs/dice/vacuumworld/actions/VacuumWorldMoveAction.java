package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;

public class VacuumWorldMoveAction implements PhysicalAction<VacuumWorldPhysicalActionsEnum> {
    private VacuumWorldPhysicalActionsEnum type;
    
    
    public VacuumWorldMoveAction() {
	this.type = VacuumWorldPhysicalActionsEnum.MOVE;
    }

    @Override
    public VacuumWorldPhysicalActionsEnum getType() {
	return this.type;
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldPhysicalActionsEnum.MOVE.equals(this.type);
    }

    @Override
    public Result attempt(Environment context, Physics physics) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Actor getActor() {
	// TODO Auto-generated method stub
	return null;
    }
}