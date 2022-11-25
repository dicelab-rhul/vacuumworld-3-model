package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;

public class VacuumWorldMindAppearance implements Appearance {
    private static final long serialVersionUID = 728932534358819029L;
    private String mindName;

    public VacuumWorldMindAppearance(String mindName) {
        this.mindName = mindName;
    }

    public String getMindName() {
        return this.mindName;
    }
}
