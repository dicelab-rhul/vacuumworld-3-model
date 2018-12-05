package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.serialization.ValidatingObjectInputStream;

import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.enums.SensorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldPrincipalListener;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAvatarAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

//This class needs to be implemented in the future.
public class VacuumWorldAvatar implements VacuumWorldActor, Serializable {
    private static final long serialVersionUID = 1270465779420663151L;
    private static final String TEMP_MSG = "TBD";
    
    public VacuumWorldAvatar(VacuumWorldAvatar toCopy) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }
    
    public VacuumWorldAvatar(String id, AvatarAppearance avatarAppearance, List<Sensor> sensors, List<Actuator> actuators, VacuumWorldPrincipalListener listener) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    public String getID() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }
    
    public VacuumWorldAvatarAppearance getAppearance() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public Map<SensorPurposeEnum, List<Sensor>> getSensors() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public List<Sensor> getSpecificSensors(SensorPurposeEnum purpose) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public Map<ActuatorPurposeEnum, List<Actuator>> getActuators() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public List<Actuator> getSpecificActuators(ActuatorPurposeEnum purpose) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public List<Sensor> getAllSensors() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public List<Actuator> getAllActuators() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public ValidatingObjectInputStream getInputChannels() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public ObjectOutputStream getOutputChannels() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void setInputChannels(ValidatingObjectInputStream input) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void setOutputChannels(ObjectOutputStream output) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void run() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public boolean isUser() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public boolean isCleaningAgent() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public boolean isAvatar() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void setStopFlag(boolean stop) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void setPauseFlag(boolean pause) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void setRunFlag(boolean simulatedRun) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public void setSocketWithEnvironment(Socket socket) {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public Socket getSocketWithEnvironment() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }

    @Override
    public boolean isPaused() {
	throw new VacuumWorldRuntimeException(VacuumWorldAvatar.TEMP_MSG);
    }
}