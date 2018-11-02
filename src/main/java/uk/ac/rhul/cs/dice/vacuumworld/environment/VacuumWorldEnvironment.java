package uk.ac.rhul.cs.dice.vacuumworld.environment;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.cloudstrife9999.logutilities.LogUtils;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcommon.enums.ContentType;
import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractEnvironment;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEvent;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldPrinter;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldSerializer;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldWhitelister;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractActionInterface;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractCommunicativeAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractPhysicalAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.physics.VacuumWorldPhysics;
import uk.ac.rhul.cs.dice.vacuumworld.perception.NothingMoreIncomingPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VWHandshakeWhitelister;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VWMessageCodes;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

public class VacuumWorldEnvironment extends AbstractEnvironment implements Runnable {
    public static final int MINIMUM_SIZE = 1; //Make this configurable sometime in the future. For now it is fine.
    public static final int MAXIMUM_SIZE = 10; //Make this configurable sometime in the future. For now it is fine.
    private int port;
    private String hostname;
    private Map<VacuumWorldCoordinates, VacuumWorldLocation> grid;
    private VacuumWorldPhysics physics;
    private ServerSocket server;
    private Map<String, ValidatingObjectInputStream> input;
    private Map<String, ObjectOutputStream> output;
    private volatile boolean stopFlag;
    private volatile boolean initializationComplete;
    private volatile boolean goodToGo;
    private int numberOfActors;
    private ValidatingObjectInputStream fromController;
    private ObjectOutputStream toController;
    private Map<String, Result> cycleResults;

    public VacuumWorldEnvironment(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid, boolean stopFlag, String hostname, int port, ObjectOutputStream toController, ValidatingObjectInputStream fromController) {
	this.grid = new ConcurrentHashMap<>(grid);
	this.toController = toController;
	this.fromController = fromController;
	this.cycleResults = new HashMap<>();

	initCommon(stopFlag, hostname, port);
	setAppearance(new VacuumWorldEnvironmentAppearance(this.grid));
    }
    
    public void setNumberOfExpectedActors(int numberOfActors) {
	this.numberOfActors = numberOfActors;
    }
    
    public boolean canAcceptConnections() {
	return this.server != null && this.server.isBound();
    }

    @Override
    public VacuumWorldEnvironmentAppearance getAppearance() {
	return (VacuumWorldEnvironmentAppearance) super.getAppearance();
    }

    private void stopServer() {
	try {
	    this.server.close();
	}
	catch (IOException e) {
	    LogUtils.log(e);
	}
    }

    private void initCommon(boolean stopFlag, String hostname, int port) {
	this.physics = new VacuumWorldPhysics();
	this.stopFlag = stopFlag;
	this.input = new HashMap<>();
	this.output = new HashMap<>();
	this.hostname = hostname;
	this.port = port;
    }

    public void initSocket() {
	try {
	    initSocketUnsafe();
	}
	catch (Exception e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private void initSocketUnsafe() throws IOException {
	this.server = new ServerSocket(this.port);

	int counter = 0;
	LogUtils.log("Waiting for the actor(s) to connect to the environment...");

	while (!this.initializationComplete) {
	    Socket socket = this.server.accept();
	    
	    LogUtils.log("An actor has announced its presence to the environment!");
	    counter++;

	    ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
	    ValidatingObjectInputStream i = new ValidatingObjectInputStream(socket.getInputStream());
	    String recipientId = i.readUTF();
	    this.input.put(recipientId, i);
	    this.output.put(recipientId, o);

	    if (counter >= this.numberOfActors) {
		break;
	    }
	    else {
		LogUtils.log("Waiting for " + (this.numberOfActors - counter) + " more actor(s) to connect to the environment...");
	    }
	}

	LogUtils.log("All the actors have successfully established a connection with the environment.");

	this.goodToGo = true;
    }

    public String getHostname() {
	return this.hostname;
    }

    public int getPort() {
	return this.port;
    }

    public boolean getStopFlag() {
	return this.stopFlag;
    }

    public void setStopFlag(boolean flag) {
	this.stopFlag = flag;
    }

    public void finishInitialization() {
	sendFirstPerception();
	
	this.initializationComplete = true;
    }

    private void sendFirstPerception() {
	while(!this.goodToGo) {
	    if(System.currentTimeMillis() % 1000000 == 0) {
		LogUtils.log("Final Fantasy VII is the best Final Fantasy!!!");
	    }
	}
	
	this.grid.entrySet().stream().filter(e -> e.getValue().containsACleaningAgent()).map(e -> e.getValue().getActorIfAny()).forEach(this::sendFirstPerceptionToActor);
    }
    
    private void sendFirstPerceptionToActor(VacuumWorldActor actor) {
	VacuumWorldSensingActionResult result = new VacuumWorldSensingActionResult(ActionResult.SUCCESS);
	provideFeedback(result, actor);
    }

    public void setInputStreams(Map<String, ValidatingObjectInputStream> input) {
	this.input = input;
    }

    public void setOutputStreams(Map<String, ObjectOutputStream> output) {
	this.output = output;
    }

    public Collection<ObjectOutputStream> getAllOutputStreams() {
	return this.output.values();
    }

    public static boolean[] getWallInfo(VacuumWorldCoordinates coordinates, int size) {
	boolean[] walls = new boolean[4];
	int i = coordinates.getX();
	int j = coordinates.getY();

	walls[0] = j == 0; // north
	walls[1] = j == size - 1; // south
	walls[2] = i == 0; // west
	walls[3] = i == size - 1; // east

	return walls;
    }

    public Map<VacuumWorldCoordinates, VacuumWorldLocation> getGrid() {
	return ImmutableMap.copyOf(this.grid);
    }

    public void listenAndExecute() {
	waitForInitialization();
	this.input.entrySet().forEach(this::listenForActorAndExecute);
	this.output.entrySet().forEach(this::sendPerceptions);
	
	informController();
	LogUtils.log(this.getClass().getSimpleName() + ": end of the cycle.\n\n--------------------\n");
	
	LogUtils.log(this.getClass().getSimpleName() + ": printing current configuration...");
	VacuumWorldPrinter.dumpModelFromLocations(this.grid);
	
	LogUtils.log(this.getClass().getSimpleName() + ": start of the cycle.\n");
	
	this.output.entrySet().forEach(this::sendLastPerception);

	long timestamp = System.nanoTime();

	while (true) {
	    if (System.nanoTime() - timestamp > (long) 750000000) {
		break;
	    }
	}
	
	if(this.fromController != null) {
	    checkForStop();
	}
    }
    
    private void sendPerceptions(Entry<String, ObjectOutputStream> output) {
	Result result = this.cycleResults.remove(output.getKey());
	provideFeedback(result, getActorFromId(output.getKey()));
    }
    
    private void sendLastPerception(Entry<String, ObjectOutputStream> output) {
	try {
	    ObjectOutputStream os = output.getValue();
	    os.reset();
	    os.writeObject(new NothingMoreIncomingPerception());
	    os.flush();
	}
	catch(IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private void checkForStop() {
	try {
	    VWHandshakeWhitelister.whitelistHandshakeClasses(this.fromController);
	    uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldMessage message = (uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldMessage) this.fromController.readObject();
	    
	    if(VWMessageCodes.QUIT_FROM_VIEW.equals(message.getCode())) {
		this.stopFlag = true;
	    }
	}
	catch(Exception e) {
	    LogUtils.log(e);
	}
    }

    private void informController() {
	try {
	    if(this.toController != null) {
		JSONObject state = new JSONObject(VacuumWorldSerializer.serialize(this).toString());
		
		LogUtils.log("Sending state to controller...");
		LogUtils.log(state.toString());
		
		uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldMessage message = new uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldMessage(VWMessageCodes.UPDATE_FROM_MODEL, state);
		
		this.toController.reset();
		this.toController.writeObject(message);
		this.toController.flush();
	    }
	}
	catch(Exception e) {
	    LogUtils.log(e);
	}
    }

    private void waitForInitialization() {
	while (true) {
	    if (this.initializationComplete && this.goodToGo) {
		break;
	    }
	}
    }

    private void listenForActorAndExecute(Entry<String, ValidatingObjectInputStream> input) {
	try {
	    ValidatingObjectInputStream is = input.getValue();
	    LogUtils.log(this.getClass().getSimpleName() + ": waiting for action from " + input.getKey() + "...");
	    
	    VacuumWorldWhitelister.whitelistEventClasses(is);
	    VacuumWorldEvent event = (VacuumWorldEvent) is.readObject();
	    LogUtils.log(this.getClass().getSimpleName() + ": got " + event.getAction().getClass().getSimpleName() + " from " + input.getKey() + "...");
		
	    VacuumWorldAbstractActionInterface action = event.getAction();
	    printActorDetailsBefore(action);
	    Result result = attemptExecution(action);
	    printActorDetailsAfter(action);
	    
	    this.cycleResults.put(action.getActorID(), result);
	}
	catch (ClassNotFoundException | IOException e) {
	    LogUtils.log(e);
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private void printActorDetailsBefore(VacuumWorldAbstractActionInterface action) {
	printActorDetails(action, "before");
    }

    private void printActorDetailsAfter(VacuumWorldAbstractActionInterface action) {
	printActorDetails(action, "after");
    }

    private void printActorDetails(VacuumWorldAbstractActionInterface action, String moment) {
	VacuumWorldActor actor = getActorFromId(action.getActorID());
	Orientation orientation = getActorOrientation(actor);
	LogUtils.log(actor.getID() + ": position " + moment + " attempt: " + this.grid.entrySet().stream().filter(e -> e.getValue().containsSuchActor(actor.getID())).findFirst().map(Entry::getKey).orElse(null) + ".");
	LogUtils.log(actor.getID() + ": orientation " + moment + " attempt: " + orientation + ".");
    }

    private Orientation getActorOrientation(VacuumWorldActor actor) {
	return actor.getAppearance().getOrientation();
    }

    private void provideFeedback(Result result, VacuumWorldActor actor) {
	if (result instanceof VacuumWorldPhysicalActionResult) {
	    provideFeedback((VacuumWorldPhysicalActionResult) result, actor);
	}
	else if (result instanceof VacuumWorldCommunicativeActionResult) {
	    provideFeedback((VacuumWorldCommunicativeActionResult) result, actor);
	}
	else if (result instanceof VacuumWorldSensingActionResult) {
	    provideFeedback((VacuumWorldSensingActionResult) result, actor);
	}
	else {
	    throw new IllegalArgumentException();
	}
    }

    private void provideFeedback(VacuumWorldPhysicalActionResult result, VacuumWorldActor actor) {
	VacuumWorldPerception perception = buildPerception(result.getActionResultType(), actor);
	sendPerception(perception, actor);
    }

    private void provideFeedback(VacuumWorldCommunicativeActionResult result, VacuumWorldActor actor) {
	VacuumWorldSpeechPerception speechPerception = buildSpeechPerception(result.getMessage(), actor);
	VacuumWorldPerception perception = buildPerception(result.getActionResultType(), actor);
	sendPerception(perception, actor);
	sendSpeechPerception(speechPerception, getRecipientsFromIDs(result.getRecipients()));
    }

    private VacuumWorldSpeechPerception buildSpeechPerception(VacuumWorldMessage message, VacuumWorldActor actor) {
	return new VacuumWorldSpeechPerception(message, actor.getAppearance());
    }

    private Set<VacuumWorldActor> getRecipientsFromIDs(Set<String> recipients) {
	return recipients.stream().map(this::getActorFromId).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private void provideFeedback(VacuumWorldSensingActionResult result, VacuumWorldActor actor) {
	VacuumWorldPerception perception = buildPerception(result.getActionResultType(), actor);
	sendPerception(perception, actor);
    }

    private VacuumWorldPerception buildPerception(ActionResult result, VacuumWorldActor actor) {
	VacuumWorldEnvironmentAppearance appearance = buildEnvironmentAppearance(actor);
	return new VacuumWorldPerception(result, appearance);
    }

    private VacuumWorldEnvironmentAppearance buildEnvironmentAppearance(VacuumWorldActor actor) {
	Orientation orientation = getActorOrientation(actor);
	
	VacuumWorldLocation location = getLocationFromActorId(actor.getID());	
	VacuumWorldLocation forward = this.grid.get(location.getCoordinates().getForwardCoordinates(orientation));
	VacuumWorldLocation left = this.grid.get(location.getCoordinates().getLeftCoordinates(orientation));
	VacuumWorldLocation right = this.grid.get(location.getCoordinates().getRightCoordinates(orientation));
	VacuumWorldLocation forwardLeft = this.grid.get(location.getCoordinates().getForwardLeftCoordinates(orientation));
	VacuumWorldLocation forwardRight = this.grid.get(location.getCoordinates().getForwardRightCoordinates(orientation));

	return buildEnvironmentAppearance(location, forward, left, right, forwardLeft, forwardRight);
    }

    private VacuumWorldEnvironmentAppearance buildEnvironmentAppearance(VacuumWorldLocation... locations) {
	Set<VacuumWorldLocation> nonNullLocations = Stream.of(locations).filter(Objects::nonNull).collect(Collectors.toSet());
	Map<VacuumWorldCoordinates, VacuumWorldLocation> locationsMap = new HashMap<>();
	nonNullLocations.forEach(location -> locationsMap.put(location.getCoordinates(), location));

	return new VacuumWorldEnvironmentAppearance(locationsMap);
    }

    private void sendPerception(VacuumWorldPerception perception, VacuumWorldActor actor) {
	sendGenericPerception(perception, actor.getID());
    }

    private void sendSpeechPerception(VacuumWorldSpeechPerception perception, Set<VacuumWorldActor> actors) {
	actors.stream().map(VacuumWorldActor::getID).forEach(id -> sendGenericPerception(perception, id));
    }

    private void sendGenericPerception(Perception perception, String recipientId) {
	try {
	    LogUtils.log(this.getClass().getSimpleName() + ": sending " + perception.getClass().getSimpleName() + " to " + recipientId + ".");
	    
	    this.output.get(recipientId).reset();
	    this.output.get(recipientId).writeObject(perception);
	    this.output.get(recipientId).flush();
	    
	    LogUtils.log(this.getClass().getSimpleName() + ": sent " + perception.getClass().getSimpleName() + " to " + recipientId + ".");
		
	}
	catch (IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private Result attemptExecution(VacuumWorldAbstractActionInterface action) {
	if (action instanceof VacuumWorldAbstractPhysicalAction) {
	    return ((VacuumWorldAbstractPhysicalAction) action).attempt(this, this.physics);
	}
	else if (action instanceof VacuumWorldAbstractCommunicativeAction) {
	    return ((VacuumWorldAbstractCommunicativeAction) action).attempt(this, this.physics);
	}
	else if (action instanceof VacuumWorldAbstractSensingAction) {
	    return ((VacuumWorldAbstractSensingAction) action).attempt(this, this.physics);
	}
	else {
	    throw new UnsupportedOperationException();
	}
    }

    public VacuumWorldLocation getLocationFromActorId(String id) {
	if (id == null) {
	    throw new IllegalArgumentException();
	}

	return this.grid.values().stream().filter(location -> location.containsSuchActor(id)).findFirst().orElse(null);
    }

    public VacuumWorldActor getActorFromId(String id) {
	if (id == null) {
	    throw new IllegalArgumentException();
	}

	return this.grid.values().stream().map(VacuumWorldLocation::getActorIfAny).filter(Objects::nonNull).filter(actor -> id.equals(actor.getID())).findFirst().orElse(null);
    }

    public void moveActor(String actorId) {
	VacuumWorldLocation location = getLocationFromActorId(actorId);
	VacuumWorldActor actor = location.removeActor();
	VacuumWorldCoordinates original = location.getCoordinates();
	Orientation orientation = getOrientation(actor);

	if (!checkTargetLocation(original, orientation)) {
	    location.addActor(actor);
	    throw new UnsupportedOperationException("The target location already has an actor.");
	}
	else {
	    this.grid.get(original.getNeighborCoordinates(orientation)).addActor(actor);
	}
    }

    public boolean checkTargetLocation(VacuumWorldCoordinates original, Orientation orientation) {
	VacuumWorldLocation location = this.grid.get(original.getNeighborCoordinates(orientation));

	return checkWallFree(original, orientation) && location != null && !location.containsAnActor();
    }

    private boolean checkWallFree(VacuumWorldCoordinates original, Orientation orientation) {
	return !this.grid.get(original).getAppearance().checkForWall(orientation);
    }

    public boolean checkOldLocation(VacuumWorldCoordinates old, String id) {
	return !this.grid.get(old).containsSuchActor(id);
    }

    public Orientation getOrientation(VacuumWorldActor actor) {
	if (actor == null) {
	    throw new IllegalArgumentException();
	}
	else {
	    return getActorOrientation(actor);
	}
    }

    @Override
    public void run() {

	LogUtils.log(this.getClass().getSimpleName() + " is being executed.");
	LogUtils.log(this.getClass().getSimpleName() + ": printing initial configuration...");

	VacuumWorldPrinter.dumpModelFromLocations(this.grid);

	LogUtils.log(this.getClass().getSimpleName() + ": printed initial configuration\n\n--------------------\n");
	LogUtils.log(this.getClass().getSimpleName() + ": start of the cycle.\n");

	while (!this.stopFlag) {
	    listenAndExecute();
	}

	stopServer();
	LogUtils.log(this.getClass().getSimpleName() + ": stop.");
    }

    @Override
    public <T> void sendData(ContentType contentType, byte[] content, Collection<T> recipientsIds) {
	//useless
    }
}