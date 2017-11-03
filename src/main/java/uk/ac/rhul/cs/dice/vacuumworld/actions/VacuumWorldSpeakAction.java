package uk.ac.rhul.cs.dice.vacuumworld.actions;

import uk.ac.rhul.cs.dice.agentactions.interfaces.CommunicativeAction;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Environment;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Physics;

public class VacuumWorldSpeakAction implements CommunicativeAction<VacuumWorldCommunicativeActionsEnum> {
    private VacuumWorldCommunicativeActionsEnum type;
    
    public VacuumWorldSpeakAction() {
	this.type = VacuumWorldCommunicativeActionsEnum.SPEAK;
    }

    @Override
    public VacuumWorldCommunicativeActionsEnum getType() {
	return this.type;
    }

    @Override
    public boolean isTypeConsistent() {
	return VacuumWorldCommunicativeActionsEnum.SPEAK.equals(this.type);
    }

    @Override
    public VacuumWorldCommunicativeActionResult attempt(Environment context, Physics physics) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Actor getActor() {
	// TODO Auto-generated method stub
	return null;
    }
}