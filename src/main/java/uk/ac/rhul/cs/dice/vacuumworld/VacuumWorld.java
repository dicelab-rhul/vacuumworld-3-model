package uk.ac.rhul.cs.dice.vacuumworld;

import java.nio.file.Paths;

import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorld {

    private VacuumWorld() {}

    public static void main(String[] args) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("example.json"), false);
	VacuumWorldEnvironment emptyEnv = new VacuumWorldEnvironment(10, false);
	VacuumWorldEnvironment bigEnv = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("big_example.json"), false);
	VacuumWorldPrinter.dumpModelFromLocations(env.getGrid());
	VacuumWorldPrinter.dumpModelFromLocations(emptyEnv.getGrid());
	VacuumWorldPrinter.dumpModelFromLocations(bigEnv.getGrid());
	VacuumWorldSerializer.dumpToFile(Paths.get(".", "example_new.json"), VacuumWorldSerializer.serialize(env));
	VacuumWorldSerializer.dumpToFile(Paths.get(".", "empty_new.json"), VacuumWorldSerializer.serialize(emptyEnv));
	VacuumWorldSerializer.dumpToFile(Paths.get(".", "big_example_new.json"), VacuumWorldSerializer.serialize(bigEnv));
	new VacuumWorldComponentsManager();
    }
}