package uk.ac.rhul.cs.dice.vacuumworld.actors;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractSensor;
import uk.ac.rhul.cs.dice.agent.enums.SensorPurposeEnum;

public class VacuumWorldSensor extends AbstractSensor {
    private static final long serialVersionUID = 1853724355290142343L;

    public VacuumWorldSensor(SensorPurposeEnum purpose) {
        super(purpose);
    }
}
