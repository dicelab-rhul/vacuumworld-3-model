package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.Arrays;
import java.util.HashSet;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorld {

    private VacuumWorld() {}

    public static void main(String[] args) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("example.json"), false);
	//VacuumWorldEnvironment env = new VacuumWorldEnvironment(9, false);
	VacuumWorldPrinter.dumpModel(env.getGrid());
	
	LogUtils.log(VacuumWorldAbstractAction.generate('M'));
	LogUtils.log(VacuumWorldAbstractAction.generate('L'));
	LogUtils.log(VacuumWorldAbstractAction.generate('R'));
	LogUtils.log(VacuumWorldAbstractAction.generate('C'));
	LogUtils.log(VacuumWorldAbstractAction.generate('D'));
	LogUtils.log(VacuumWorldAbstractAction.generate('S'));
	LogUtils.log(VacuumWorldAbstractAction.generate('T', new VacuumWorldMessage("Hello World"), new HashSet<>(Arrays.asList("A1, A2"))));
	LogUtils.log(VacuumWorldAbstractAction.generate('B', new VacuumWorldMessage("Hello World")));
    }
}