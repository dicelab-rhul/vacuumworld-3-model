package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.cloudstrife9999.logutilities.LogUtils;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.perception.StopPerception;

public class VacuumWorldComponentsManager {
    private String hostname;
    private int port;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ExecutorService executor;
    private VacuumWorldUniverse universe;
    private static final String DEBUG_CONFIGURATION = "two_agents.json";
    
    
    public VacuumWorldComponentsManager(boolean fromFile, boolean simulatedRun, String hostname, int port) throws IOException {
	this.hostname = hostname;
	this.port = port;
	
	setupServer(fromFile);
	createUniverse(fromFile, simulatedRun);
	startUniverse();
	stopUniverse();
    }

    private void stopUniverse() {
	try {
	    Thread.sleep(10000);
	}
	catch (InterruptedException e) {
	    LogUtils.log(this.getClass().getSimpleName() + ": main thread interrupted: stopping everything!");
	    
	    shutdown();
	    Thread.currentThread().interrupt();
	}
	
	LogUtils.log(this.getClass().getSimpleName() + ": time up! Stopping everything!");
	
	shutdown();
}
    
    private void shutdown() {
	try {
	    setStopFlag(true);
	    this.universe.getMainAmbient().getAllOutputStreams().forEach(this::shutdownChannel);
	    this.executor.shutdownNow();
	    this.executor.awaitTermination(5, TimeUnit.SECONDS);
	}
	catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}
	finally {
	    System.exit(0); //just in case.
	}
    }

    private void shutdownChannel(ObjectOutputStream o) {
	try {
	    o.writeObject(new StopPerception());
	    o.flush();
	}
	catch(IOException e) {
	    LogUtils.log(e);
	}
    }

    private void startUniverse() {	
	this.executor = Executors.newFixedThreadPool(1 + this.universe.countActiveBodies());
	
	LogUtils.log(this.getClass().getSimpleName() + ": starting environment...");
	this.executor.submit(this.universe.getEnvironment());
	
	LogUtils.log(this.getClass().getSimpleName() + ": starting actors...");
	this.universe.getAllActors().forEach(actor -> actor.setStopFlag(this.universe.getEnvironment().getStopFlag()));
	this.universe.getAllActors().forEach(this.executor::submit);
    }

    private void createUniverse(boolean fromFile, boolean simulatedRun) {
	LogUtils.log(this.getClass().getSimpleName() + ": starting universe...");
	
	if(fromFile) {
	    createUniverseForDebug(DEBUG_CONFIGURATION, simulatedRun);
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

    private void setupServer(boolean fromFile) throws IOException {
	LogUtils.log(this.getClass().getSimpleName() + ": starting listener for Controller...");
	
	if(!fromFile) {
	    this.output = new ObjectOutputStream(System.out);
	    this.input = new ObjectInputStream(System.in);
	}
    }

    private void createUniverse(JsonObject initialConfiguration) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(initialConfiguration), false, this.hostname, this.port);
	this.universe = new VacuumWorldUniverse(env, this.hostname, this.port);
    }
    
    private void createUniverseForDebug(String path, boolean simulatedRun) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(path), false, this.hostname, this.port);
	this.universe = new VacuumWorldUniverse(env, this.hostname, this.port);
	this.universe.getAllActors().forEach(actor -> actor.setRunFlag(simulatedRun));
    }

    private void setStopFlag(boolean flag) {
	this.universe.getEnvironment().setStopFlag(flag);
	this.universe.getAllActors().forEach(actor -> actor.setStopFlag(this.universe.getEnvironment().getStopFlag()));
    }
}