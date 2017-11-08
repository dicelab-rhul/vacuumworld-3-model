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
	
	LogUtils.log(VacuumWorldAbstractAction.generate('M').toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('L').toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('R').toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('C').toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('D').toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('S').toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('T', new VacuumWorldMessage("Hello World"), new HashSet<>(Arrays.asList("A1, A2"))).toString());
	LogUtils.log(VacuumWorldAbstractAction.generate('B', new VacuumWorldMessage("Hello World")).toString());
    }
}