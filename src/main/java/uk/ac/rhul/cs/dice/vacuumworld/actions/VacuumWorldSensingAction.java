package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentactions.interfaces.SensingAction;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;

public class VacuumWorldSensingAction implements SensingAction<VacuumWorldSensingActionsEnum> {
    private VacuumWorldSensingActionsEnum type;
    
    public VacuumWorldSensingAction() {
	this.type = VacuumWorldSensingActionsEnum.SENSE;
    }

    @Override
    public VacuumWorldSensingActionsEnum getType() {
	return this.type;
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldSensingActionsEnum.SENSE.equals(this.type);
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