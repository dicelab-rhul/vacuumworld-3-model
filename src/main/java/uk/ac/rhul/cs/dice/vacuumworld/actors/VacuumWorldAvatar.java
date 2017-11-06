package uk.ac.rhul.cs.dice.vacuumworld.actors;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAvatar;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agent.interfaces.Actuator;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agent.interfaces.AvatarAppearance;
import uk.ac.rhul.cs.dice.agent.interfaces.PrincipalListener;
import uk.ac.rhul.cs.dice.agent.interfaces.Sensor;
import uk.ac.rhul.cs.dice.agentactions.enums.EnvironmentalActionType;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAvatarAppearance;

public class VacuumWorldAvatar extends AbstractAvatar {
    private static final long serialVersionUID = 7363668279670343837L;

    public VacuumWorldAvatar(String id, AvatarAppearance appearance, List<Sensor> sensors, List<Actuator> actuators, PrincipalListener principalListener) {
	super(id, appearance, sensors, actuators, principalListener);
    }
    
    public VacuumWorldAvatar(VacuumWorldAvatar toCopy) {
	this(toCopy.getID(), toCopy.getAppearance(), toCopy.getAllSensors(), toCopy.getAllActuators(), toCopy.getPrincipalListener());
    }

    @Override
    public void sendFeedbackToPrincipal(Analyzable... feedback) {
	throw new UnsupportedOperationException(); //TODO maybe change this
    }

    @Override
    public void sendToActuator(Action<?> action) {
	EnvironmentalActionType type = (EnvironmentalActionType) action.getGenericType();
	((VacuumWorldActuator) getActuatorFromActionType(type)).validateExecution(action);
    }
    
    private Actuator getActuatorFromActionType(EnvironmentalActionType type) {
	switch (type) {
	case PHYSICAL:
	    return getSpecificActuators(ActuatorPurposeEnum.ACT_PHYSICALLY).get(0);
	case COMMUNICATIVE:
	    return getSpecificActuators(ActuatorPurposeEnum.SPEAK).get(0);
	case SENSING:
	    return getSpecificActuators(ActuatorPurposeEnum.OTHER).get(0);
	default:
	    throw new UnsupportedOperationException("No compatible actuator found.");
	}
    }

    @Override
    public ObjectInputStream getInputChannels() {
	throw new UnsupportedOperationException();
    }

    @Override
    public ObjectOutputStream getOutputChannels() {
	throw new UnsupportedOperationException();
    }

    @Override
    public void setInputChannels(ObjectInputStream input) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void setOutputChannels(ObjectOutputStream output) {
	throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
	VacuumWorldAbstractAction action = (VacuumWorldAbstractAction) getPrincipalListener().decide();
	getPrincipalListener().execute((Action<?>) action);
	sendToActuator((Action<?>) action);
	sendToEnvironment();
    }

    private void sendToEnvironment() {
	//TODO
    }

    @Override
    public void turnLeft() {
	((VacuumWorldAvatarAppearance) getAppearance()).turnLeft();
    }

    @Override
    public void turnRight() {
	((VacuumWorldAvatarAppearance) getAppearance()).turnRight();
    }
}