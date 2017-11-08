package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.logging.ConsoleHandler;

public class VacuumWorldConsoleHandler extends ConsoleHandler {

    public VacuumWorldConsoleHandler() {
	super();
	
	setOutputStream(System.out);
    }
}