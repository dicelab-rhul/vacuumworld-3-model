package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.util.List;

import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AgentMind;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldUserMind;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;

public class VacuumWorldCleaningAgent extends VacuumWorldAbstractActor {
    private static final long serialVersionUID = -7231158706838196637L;

    public VacuumWorldCleaningAgent(String id, VacuumWorldActorAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, AgentMind mind) {
        super(id, appearance, sensors, actuators, mind);

        checkForForbiddenMindParents(mind, getClass(), VacuumWorldUserMind.class);
    }

    public VacuumWorldCleaningAgent(VacuumWorldCleaningAgent toCopy) {
        this(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getMind());
    }
}
