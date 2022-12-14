package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import java.util.Random;

import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;

public enum VacuumWorldDirtColor {
    GREEN, ORANGE;

    private static Random rng = new Random();

    public boolean canBeCleanedBy(AgentColor agentColor) {
        switch (this) {
            case GREEN:
                return AgentColor.GREEN.equals(agentColor) || AgentColor.WHITE.equals(agentColor);
            case ORANGE:
                return AgentColor.ORANGE.equals(agentColor) || AgentColor.WHITE.equals(agentColor);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public char toChar() {
        switch (this) {
            case GREEN:
                return 'g';
            case ORANGE:
                return 'o';
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static VacuumWorldDirtColor random() {
        if (VacuumWorldDirtColor.rng.nextBoolean()) {
            return VacuumWorldDirtColor.GREEN;
        }
        else {
            return VacuumWorldDirtColor.ORANGE;
        }
    }
}
