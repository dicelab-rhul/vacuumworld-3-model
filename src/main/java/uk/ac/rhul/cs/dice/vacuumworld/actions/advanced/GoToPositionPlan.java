package uk.ac.rhul.cs.dice.vacuumworld.actions.advanced;

import java.util.ArrayDeque;
import java.util.Deque;

import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.PhysicalAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldIdleAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.enums.VacuumWorldPhysicalActionsEnum;

public class GoToPositionPlan implements VacuumWorldPlan<PhysicalAction<VacuumWorldPhysicalActionsEnum>> {
    private Deque<PhysicalAction<VacuumWorldPhysicalActionsEnum>> actions;
    private VacuumWorldAbstractAction last;

    public GoToPositionPlan() {
	this.actions = new ArrayDeque<>();
	this.last = new VacuumWorldIdleAction();
    }
    
    @Override
    public boolean isEmpty() {
	return this.actions.isEmpty();
    }

    @Override
    public int getSize() {
	return this.actions.size();
    }

    public VacuumWorldAbstractAction next(ActionResult lastResult) {
	if(!ActionResult.SUCCESS.equals(lastResult)) {
	    return last;
	}
	else {
	    return (VacuumWorldAbstractAction) pop();
	}
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
	    this.last = (VacuumWorldAbstractAction) this.actions.peek();
	    
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
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append("Number of actions: ");
        builder.append(this.actions.size());
        builder.append(". Representation: ");
        
        this.actions.forEach(a -> builder.append(((VacuumWorldAbstractAction) a).toShortString()));
        
        return builder.toString();
    }
}