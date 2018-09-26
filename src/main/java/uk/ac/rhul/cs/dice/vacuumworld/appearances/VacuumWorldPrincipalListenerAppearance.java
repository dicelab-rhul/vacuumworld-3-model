package uk.ac.rhul.cs.dice.vacuumworld.appearances;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Appearance;

public class VacuumWorldPrincipalListenerAppearance implements Appearance {
    private static final long serialVersionUID = -7214127106664579531L;
    private String name;
    private int port;
    
    public VacuumWorldPrincipalListenerAppearance(String listenerClassName, int port) {
	this.name = listenerClassName;
	this.port = port;
    }
    
    public int getPort() {
	return this.port;
    }
    
    public String getName() {
	return this.name;
    }
}