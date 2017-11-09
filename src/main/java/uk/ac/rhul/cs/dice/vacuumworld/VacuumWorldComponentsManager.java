package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VacuumWorldComponentsManager {
    private ExecutorService executor;
    private VacuumWorldUniverse universe;
    
    public VacuumWorldComponentsManager() {
	this.universe = new VacuumWorldUniverse(null);
	this.universe.getAllCleaningAgents().forEach(agent -> agent.setStopFlag(false));
	this.executor = Executors.newFixedThreadPool(1 + this.universe.getAllCleaningAgents().size());
	this.executor.submit(this.universe.getEnvironment());
	this.universe.getAllCleaningAgents().forEach(this.executor::submit);
	
	try {
	    Thread.sleep(10000);
	}
	catch (InterruptedException e) {
	    System.out.println("Main thread interrupted: stopping everything!");
	    
	    this.universe.getAllCleaningAgents().forEach(agent -> agent.setStopFlag(true));
	    this.universe.getEnvironment().setStopFlag(true);
	    this.executor.shutdownNow();
	    Thread.currentThread().interrupt();
	}
	
	System.out.println("Stopping everything!");
	
	this.universe.getAllCleaningAgents().forEach(agent -> agent.setStopFlag(true));
	this.universe.getEnvironment().setStopFlag(true);
	this.executor.shutdownNow();
    }
}