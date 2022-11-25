package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldSerializer;
import uk.ac.rhul.cs.dice.vacuumworld.actors.ActorType;

public class VacuumWorldAvatarAppearance implements VacuumWorldActorAppearance {
    private static final long serialVersionUID = 6754120129442366599L;
    private String id;
    private Orientation orientation;
    private int listeningPort;
    private VacuumWorldPrincipalListenerAppearance mindAppearance;
    private List<Sensor> sensors;
    private List<Actuator> actuators;

    public VacuumWorldAvatarAppearance(String id, Orientation orientation, int listeningPort, VacuumWorldPrincipalListenerAppearance mindAppearance, List<Sensor> sensors, List<Actuator> actuators) {
        this.id = id;
        this.orientation = orientation;
        this.listeningPort = listeningPort;
        this.mindAppearance = mindAppearance;
        this.sensors = ImmutableList.copyOf(sensors);
        this.actuators = ImmutableList.copyOf(actuators);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Orientation getOrientation() {
        return this.orientation;
    }

    @Override
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public int getListeningPort() {
        return this.listeningPort;
    }

    @Override
    public VacuumWorldPrincipalListenerAppearance getMindAppearance() {
        return this.mindAppearance;
    }

    @Override
    public List<Actuator> getActuators() {
        return this.actuators;
    }

    @Override
    public List<Sensor> getSensors() {
        return this.sensors;
    }

    @Override
    public JsonObject serialize() {
        return VacuumWorldSerializer.serialize(this);
    }

    @Override
    public ActorType getType() {
        return ActorType.AVATAR;
    }
}
