package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.cloudstrife9999.logutilities.LogUtils;
import org.json.JSONObject;

import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.perception.StopPerception;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VWMessageCodes;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldMessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VacuumWorldComponentsManager {
    private int controllerPort;
    private ServerSocket forController;
    private Socket socketWithController;
    private String hostname;
    private int port;
    private OutputStream out;
    private InputStream in;
    private ObjectInputStream fromController;
    private ObjectOutputStream toController;
    private VacuumWorldMessage latestFromController;
    private ExecutorService executor;
    private VacuumWorldUniverse universe;
    private static List<String> ids = new ArrayList<>();

    /**
     * ONLINE
     * 
     * @param simulatedRun
     * @param hostname
     * @param port
     * @throws IOException
     */
    public VacuumWorldComponentsManager(boolean simulatedRun, String hostname, int controllerPort, int environmentPort) throws IOException {
	this.hostname = hostname;
	this.port = environmentPort;
	this.controllerPort = controllerPort;

	setupServer(false);
	
	LogUtils.log(this.getClass().getSimpleName() + ": starting universe...?");
	JSONObject initialConfiguration = waitForConnection();
	
	LogUtils.log("Model here: the raw initial state has been received!");
	
	if(initialConfiguration == null) {
	    System.out.println("null");
	}
	
	System.out.println(initialConfiguration.toString(4));
	
	JsonParser parser = new JsonParser();
	JsonObject initial = parser.parse(initialConfiguration.toString()).getAsJsonObject();
	
	System.out.println(initial.toString());
	
	LogUtils.log("Model here: the initial state has been parsed!");
	
	createUniverse(initial);

	//startUniverse();
	//stopUniverse();
    }

    /**
     * FROM FILE (DEBUG)
     * 
     * @param file
     * @param environmentPort
     * @throws IOException
     */
    public VacuumWorldComponentsManager(String file, int controllerPort, int environmentPort) throws IOException {
	this.hostname = InetAddress.getLocalHost().getHostAddress();
	this.port = environmentPort;
	this.controllerPort = controllerPort;
	
	setupServer(true);
	LogUtils.log(this.getClass().getSimpleName() + ": starting universe in debug mode...");
	createUniverseForDebug(file, false);
	// startUniverse();
	// stopUniverse();
    }

    public static List<String> getIds() {
	return VacuumWorldComponentsManager.ids;
    }
    
    public static void addId(String id) {
	VacuumWorldComponentsManager.ids.add(id);
    }
    
    public void stopUniverse() {
	while(!this.universe.getMainAmbient().getStopFlag()) {
	    continue;
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
	    System.exit(0); // just in case.
	}
    }

    private void shutdownChannel(ObjectOutputStream o) {
	try {
	    o.writeObject(new StopPerception());
	    o.flush();
	}
	catch (IOException e) {
	    LogUtils.log(e);
	}
    }

    public void startUniverse() {
	this.executor = Executors.newFixedThreadPool(1 + this.universe.countActiveBodies());

	LogUtils.log(this.getClass().getSimpleName() + ": starting environment...");
	this.executor.submit(this.universe.getEnvironment());

	LogUtils.log(this.getClass().getSimpleName() + ": starting actors...");
	this.universe.getAllActors().forEach(actor -> actor.setStopFlag(this.universe.getEnvironment().getStopFlag()));
	this.universe.getAllActors().forEach(this.executor::submit);
	
	stopUniverse();
    }

    private JSONObject waitForConnection() throws IOException {
	LogUtils.log("Waiting for the initial state...");
	
	try {
	    VacuumWorldMessage message = (VacuumWorldMessage) this.fromController.readObject();
	    
	    return message.getContent();
	}
	catch(Exception e) {
	    throw new IOException(e);
	}
    }

    private void setupServer(boolean fromFile) throws IOException {
	LogUtils.log(this.getClass().getSimpleName() + ": starting listener for Controller...");

	if (!fromFile) {
	    initConnection();
	}
    }
    
    public void initConnection() {
	try {
	    this.forController = new ServerSocket(this.controllerPort);
	    
	    LogUtils.log("Model here: waiting for connections...");
	    
	    this.socketWithController = this.forController.accept();
	    
	    LogUtils.log("Model here: a controller attempted a connection: " + this.socketWithController.getRemoteSocketAddress() + ".");
	    
	    this.out = this.socketWithController.getOutputStream();
	    this.in = this.socketWithController.getInputStream();
	    this.toController = new ObjectOutputStream(this.out);
	    this.fromController = new ObjectInputStream(this.in);
	    
	    doHandshake();
	}
	catch(Exception e) {
	    LogUtils.log(e);
	}
    }

    private void doHandshake() {
	LogUtils.log("Model here: waiting for the first handshake message...");
	
	receiveHCM();
	receiveHVM();
	
	LogUtils.log("Model here: handshake completed!");
    }

    private void receiveHCM() {
	try {
	    this.latestFromController = (VacuumWorldMessage) this.fromController.readObject();
	    parseHCM();
	    sendHMC();
	}
	catch(Exception e) {
	    LogUtils.log(e);
	}	
    }

    private void sendHMC() throws IOException {
	sendTo(this.toController, new VacuumWorldMessage(VWMessageCodes.HELLO_CONTROLLER_FROM_MODEL, null));
    }

    private void receiveHVM() {
	try {
	    this.latestFromController = (VacuumWorldMessage) this.fromController.readObject();
	    parseHVM();
	    sendHMV();
	}
	catch(Exception e) {
	    LogUtils.log(e);
	}
    }

    private void sendHMV() throws IOException {
	sendTo(this.toController, new VacuumWorldMessage(VWMessageCodes.HELLO_VIEW_FROM_MODEL, null));
    }
    
    private void sendTo(ObjectOutputStream to, VacuumWorldMessage message) throws IOException {
	LogUtils.log("Model here: sending" + message.getCode() + " to the controller...");
	
	to.reset();
	to.writeObject(message);
	to.flush();
    }
    
    private void parseHCM() {
	parseMessageType(VWMessageCodes.HELLO_MODEL_FROM_CONTROLLER, this.latestFromController);
    }
    
    private void parseHVM() {
	parseMessageType(VWMessageCodes.HELLO_MODEL_FROM_VIEW, this.latestFromController);
    }    
    
    private void parseMessageType(VWMessageCodes expected, VacuumWorldMessage message) {
	VWMessageCodes receivedCode = message.getCode();
	
	if(!expected.equals(receivedCode)) {
	    throw new IllegalArgumentException("Expected" + expected + ", got " + receivedCode + " instead.");
	}
	else {
	    LogUtils.log("Model here: received " + receivedCode + " from the controller.");
	}
    }

    private void createUniverse(JsonObject initialConfiguration) {
	LogUtils.log("Model here: attempting to create the universe...");
	
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(initialConfiguration), false, this.hostname, this.port, this.toController, this.fromController);
	
	this.universe = new VacuumWorldUniverse(env, this.hostname, this.port);
    }

    private void createUniverseForDebug(String path, boolean simulatedRun) {
	VacuumWorldEnvironment env = new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration(path), false, this.hostname, this.port, this.toController, this.fromController);
	
	this.universe = new VacuumWorldUniverse(env, this.hostname, this.port);
	this.universe.getAllActors().forEach(actor -> actor.setRunFlag(simulatedRun));
    }

    private void setStopFlag(boolean flag) {
	this.universe.getEnvironment().setStopFlag(flag);
	this.universe.getAllActors().forEach(actor -> actor.setStopFlag(this.universe.getEnvironment().getStopFlag()));
    }
}