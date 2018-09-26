package uk.ac.rhul.cs.dice.vacuumworld.actions.advanced;

import java.util.ArrayDeque;
import java.util.Deque;

import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;

public class GoToPositionPlan implements VacuumWorldPlan<PhysicalAction<VacuumWorldPhysicalActionsEnum>> {
    private Deque<PhysicalAction<VacuumWorldPhysicalActionsEnum>> actions;

    public GoToPositionPlan() {
	this.actions = new ArrayDeque<>();
    }
    
    @Override
    public boolean isEmpty() {
	return this.actions.isEmpty();
    }

    @Override
    public int getSize() {
	return this.actions.size();
    }

    @Override
    public PhysicalAction<VacuumWorldPhysicalActionsEnum> peek() {
	return this.actions.peek();
    }

    @Override
    public void enqueueAction(PhysicalAction<VacuumWorldPhysicalActionsEnum> action) {
	this.actions.add(action);
    }

    @Override
    public void pushActionAtTheBeginning(PhysicalAction<VacuumWorldPhysicalActionsEnum> action) {
	this.actions.push(action);
    }

    @Override
    public PhysicalAction<VacuumWorldPhysicalActionsEnum> pop() {
	if(!isEmpty()) {
	    return this.actions.pop();
	}
	
	return null;
    }

    @Override
    public void deleteLast() {
	if(!isEmpty()) {
	    this.actions.removeLast();
	}
    }
    
    @Override
    public void clear() {
        this.actions.clear();
    }
}