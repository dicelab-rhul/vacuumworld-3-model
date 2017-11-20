package uk.ac.rhul.cs.dice.vacuumworld.buildtasks;

import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorldEnvironmentBuilderTask implements Runnable {

    private VacuumWorldEnvironment environment;
    
    public VacuumWorldEnvironmentBuilderTask(VacuumWorldEnvironment environment) {
	this.environment = environment;
    }
    
    @Override
    public void run() {
	if(this.environment != null) {
	    this.environment.initSocket();
	}
    }
}