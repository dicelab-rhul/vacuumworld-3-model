package uk.ac.rhul.cs.dice.vacuumworld.actors;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractActuator;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;
import uk.ac.rhul.cs.dice.agentactions.enums.EnvironmentalActionType;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;

public class VacuumWorldActuator extends AbstractActuator {
    private static final long serialVersionUID = 3229911760231407824L;

    public VacuumWorldActuator(ActuatorPurposeEnum purpose) {
        super(purpose);
    }

    public void validateExecution(Action<?> a) {
        switch ((EnvironmentalActionType) a.getGenericType()) {
            case PHYSICAL:
                dealWithPhysicalAction();
                break;
            case COMMUNICATIVE:
                dealWithCommunicativeAction();
                break;
            case SENSING:
                dealWithSensingAction();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void dealWithPhysicalAction() {
        if (!ActuatorPurposeEnum.ACT_PHYSICALLY.equals(getPurpose())) {
            throw new UnsupportedOperationException();
        }
    }

    private void dealWithCommunicativeAction() {
        if (!ActuatorPurposeEnum.SPEAK.equals(getPurpose())) {
            throw new UnsupportedOperationException();
        }
    }

    private void dealWithSensingAction() {
        if (!ActuatorPurposeEnum.OTHER.equals(getPurpose())) {
            throw new UnsupportedOperationException();
        }
    }
}
