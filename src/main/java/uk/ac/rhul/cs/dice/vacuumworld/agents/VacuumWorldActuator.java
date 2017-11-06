package uk.ac.rhul.cs.dice.vacuumworld.agents;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractActuator;
import uk.ac.rhul.cs.dice.agent.enums.ActuatorPurposeEnum;

public class VacuumWorldActuator extends AbstractActuator {
    private static final long serialVersionUID = 3229911760231407824L;

    public VacuumWorldActuator(ActuatorPurposeEnum purpose) {
	super(purpose);
    }
}