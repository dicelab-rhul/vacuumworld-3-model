package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorldComponentsManager {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ExecutorService executor;
    private VacuumWorldUniverse universe;
    
    
    public VacuumWorldComponentsManager(boolean test) throws IOException {
	setupServer(test);
	createUniverse(test);
	startUniverse();
	stopUniverse();
    }

    private void stopUniverse() {
	try {
	    Thread.sleep(10000);
	}
	catch (InterruptedException e) {
	    System.out.println("Main thread interrupted: stopping everything!");
	    
	    shutdown();
	    Thread.currentThread().interrupt();
	}
	
	System.out.println("Stopping everything!");
	
	shutdown();
}
    
    private void shutdown() {
	setStopFlag(true);
	this.executor.shutdownNow();
    }

    private void startUniverse() {
	this.executor = Executors.newFixedThreadPool(1 + this.universe.countActiveBodies());
	this.executor.submit(this.universe.getEnvironment());
	
	setStopFlagForActiveBodies(); //this sets the stop flag to false --> it starts active bodies.
	
	this.universe.getAllCleaningAgents().forEach(this.executor::submit);
	this.universe.getAllUsers().forEach(this.executor::submit);
	this.universe.getAllAvatars().forEach(this.executor::submit);
    }

    private void createUniverse(boolean test) {
	if(test) {
	    createUniverseForDebug("easy.json");
	}
	else {
	    JsonObject initialConfiguration = waitForConnection();
	    createUniverse(initialConfiguration);
	}
    }

    private JsonObject waitForConnection() {
	// TODO Auto-generated method stub
	return null;
    }

    private void setupServer(boolean test) throws IOException {
	if(!test) {
	    this.output = new ObjectOutputStream(System.out);
	    this.input = new ObjectInputStream(System.in);
	}
    }

    private void createUniverse(JsonObject initialConfiguration) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(initialConfiguration), false);
	this.universe = new VacuumWorldUniverse(env);
    }
    
    private void createUniverseForDebug(String path) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(path), false);
	this.universe = new VacuumWorldUniverse(env);
	
	toggleTest();
    }
    
    private void toggleTest() {
	this.universe.getAllCleaningAgents().forEach(VacuumWorldCleaningAgent::toggleTest);
	this.universe.getAllUsers().forEach(VacuumWorldUserAgent::toggleTest);
	this.universe.getAllAvatars().forEach(VacuumWorldAvatar::toggleTest);
    }

    private void setStopFlag(boolean flag) {
	this.universe.getEnvironment().setStopFlag(flag);
	
	setStopFlagForActiveBodies();
    }
    
    private void setStopFlagForActiveBodies() {
	this.universe.getAllCleaningAgents().forEach(agent -> agent.setStopFlag(this.universe.getEnvironment().getStopFlag()));
	this.universe.getAllUsers().forEach(user -> user.setStopFlag(this.universe.getEnvironment().getStopFlag()));
	this.universe.getAllAvatars().forEach(avatar -> avatar.setStopFlag(this.universe.getEnvironment().getStopFlag()));
    }
}