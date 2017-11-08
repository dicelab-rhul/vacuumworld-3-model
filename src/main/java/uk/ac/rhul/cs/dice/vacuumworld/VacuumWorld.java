package uk.ac.rhul.cs.dice.vacuumworld;

import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorld {

    private VacuumWorld() {}

    public static void main(String[] args) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("example.json"), false);
	//VacuumWorldEnvironment env = new VacuumWorldEnvironment(9, false);
	VacuumWorldPrinter.dumpModel(env.getGrid());
    }
}