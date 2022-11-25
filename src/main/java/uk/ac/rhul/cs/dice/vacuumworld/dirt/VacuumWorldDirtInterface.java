package uk.ac.rhul.cs.dice.vacuumworld.dirt;

import java.io.Serializable;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;

public interface VacuumWorldDirtInterface extends Serializable {
    public abstract VacuumWorldDirtAppearance getAppearance();

    public abstract JsonObject serialize();
}
