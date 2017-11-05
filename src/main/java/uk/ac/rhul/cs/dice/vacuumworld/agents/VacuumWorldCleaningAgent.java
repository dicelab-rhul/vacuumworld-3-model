package uk.ac.rhul.cs.dice.vacuumworld.agents;

import java.util.List;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldCleaningAgent extends AbstractAgent {
    private static final long serialVersionUID = -7231158706838196637L;

    public VacuumWorldCleaningAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
	super(id, appearance, sensors, actuators, mind);
    }

    public VacuumWorldCleaningAgent(VacuumWorldCleaningAgent toCopy) {
	super(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }
    
    @Override
    public void sendToMind(Analyzable analyzable) {
	// TODO Auto-generated method stub
    }

    @Override
    public void sendToActuator(Action<?> action) {
	// TODO Auto-generated method stub
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub

    }
    
    @Override
    public void turnLeft() {
	((VacuumWorldActorAppearance) getAppearance()).turnLeft();
    }
    
    @Override
    public void turnRight() {
	((VacuumWorldActorAppearance) getAppearance()).turnRight();
    }
}