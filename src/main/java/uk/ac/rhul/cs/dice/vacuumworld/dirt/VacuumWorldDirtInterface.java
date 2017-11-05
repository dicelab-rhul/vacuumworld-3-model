package uk.ac.rhul.cs.dice.vacuumworld.dirt;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;

@FunctionalInterface
public interface VacuumWorldDirtInterface {
    public abstract VacuumWorldDirtAppearance getAppearance();
}