package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.cloudstrife9999.logutilities.LogUtils;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;

public class VacuumWorldComponentsManager {
    private String hostname;
    private int port;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ExecutorService executor;
    private VacuumWorldUniverse universe;
    
    
    public VacuumWorldComponentsManager(boolean test, String hostname, int port) throws IOException {
	this.hostname = hostname;
	this.port = port;
	
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
	    LogUtils.log("Main thread interrupted: stopping everything!");
	    
	    shutdown();
	    Thread.currentThread().interrupt();
	}
	
	LogUtils.log("Stopping everything!");
	
	shutdown();
}
    
    private void shutdown() {
	try {
	    setStopFlag(true);
	    this.executor.shutdownNow();
	    this.executor.awaitTermination(5, TimeUnit.SECONDS);
	}
	catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}
	finally {
	    System.exit(0);
	}
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
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(initialConfiguration), false, this.hostname, this.port);
	this.universe = new VacuumWorldUniverse(env, this.hostname, this.port);
    }
    
    private void createUniverseForDebug(String path) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(path), false, this.hostname, this.port);
	this.universe = new VacuumWorldUniverse(env, this.hostname, this.port);
	
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