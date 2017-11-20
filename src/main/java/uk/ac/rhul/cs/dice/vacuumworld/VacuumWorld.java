package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;

public class VacuumWorld {

    private VacuumWorld() {}

    public static void main(String[] args) throws IOException {
	//String[] hostDetails = parseHostDetails(args);
	
	new VacuumWorldComponentsManager(true, "127.0.0.1", 65000); //TODO change
    }

    private static String[] parseHostDetails(String[] args) {
	// TODO Auto-generated method stub
	return null;
    }
}