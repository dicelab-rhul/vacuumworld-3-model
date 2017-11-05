package uk.ac.rhul.cs.dice.vacuumworld;

import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;

public class VacuumWorld {

    private VacuumWorld() {}

    public static void main(String[] args) {
	System.out.println(Orientation.NORTH);
	System.out.println(Orientation.NORTH.getLeft());
	System.out.println(Orientation.NORTH.getLeft().getLeft());
	System.out.println(Orientation.NORTH.getLeft().getLeft().getLeft());
	System.out.println(Orientation.NORTH.getLeft().getLeft().getLeft().getLeft());
    }
}