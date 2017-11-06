package uk.ac.rhul.cs.dice.vacuumworld.environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agent.interfaces.Perception;
import uk.ac.rhul.cs.dice.agentactions.enums.ActionResult;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractEnvironment;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEvent;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractActionInterface;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractCommunicativeAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractPhysicalAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.messages.VacuumWorldMessage;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldCommunicativeActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldPhysicalActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.actions.results.VacuumWorldSensingActionResult;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.physics.VacuumWorldPhysics;
import uk.ac.rhul.cs.dice.vacuumworld.exceptions.VacuumWorldRuntimeException;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldPerception;
import uk.ac.rhul.cs.dice.vacuumworld.perception.VacuumWorldSpeechPerception;

public class VacuumWorldEnvironment extends AbstractEnvironment implements Runnable {
    public static final int MINIMUM_SIZE = 3;
    public static final int MAXIMUM_SIZE = 10;
    private static final double LOADING_FACTOR = 0.75;
    private Map<VacuumWorldCoordinates, VacuumWorldLocation> grid;
    private int size;
    private VacuumWorldPhysics physics;
    private Map<String, ObjectInputStream> input;
    private Map<String, ObjectOutputStream> output;
    private volatile boolean stopFlag;
    
    public VacuumWorldEnvironment(int dimension, boolean stopFlag) {
	int upperSize = dimension > MAXIMUM_SIZE ? MAXIMUM_SIZE : dimension;
	this.size = dimension < MINIMUM_SIZE ? MINIMUM_SIZE : upperSize;
	this.grid = new ConcurrentHashMap<>(1 + (int) (this.size * this.size / LOADING_FACTOR));
	this.physics = new VacuumWorldPhysics();
	this.stopFlag = stopFlag;
	
	initGrid();
	setAppearance(new VacuumWorldEnvironmentAppearance(this.grid));
    }
    
    public VacuumWorldEnvironment(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid, boolean stopFlag) {
	this.size = (int) Math.sqrt(grid.size());
	this.grid = new ConcurrentHashMap<>(grid);
	this.physics = new VacuumWorldPhysics();
	this.stopFlag = stopFlag;
	
	setAppearance(new VacuumWorldEnvironmentAppearance(this.grid));
    }
    
    public void setInputStreams(Map<String, ObjectInputStream> input) {
	this.input = input;
    }
    
    public void setOutputStreams(Map<String, ObjectOutputStream> output) {
	this.output = output;
    }

    private void initGrid() {
	for(int i = 0; i < this.size; i++) {
	    for(int j = 0; j < this.size; j++) {
		VacuumWorldCoordinates coordinates = new VacuumWorldCoordinates(i, j);
		this.grid.put(coordinates, new VacuumWorldLocation(coordinates, getWallInfo(coordinates, this.size)));
	    }
	}
    }

    public static boolean[] getWallInfo(VacuumWorldCoordinates coordinates, int size) {
	boolean[] walls = new boolean[4];
	int i = coordinates.getX();
	int j = coordinates.getY();
	
	walls[0] = j == 0; //north
	walls[1] = j == size - 1; //south
	walls[2] = i == 0; //west
	walls[3] = i == size - 1; //east
	
	return walls;
    }

    public Map<VacuumWorldCoordinates, VacuumWorldLocation> getGrid() {
	return ImmutableMap.copyOf(this.grid);
    }
    
    public synchronized void listenAndExecute() {
	this.input.values().forEach(this::listenForActorAndExecute);
    }

    private void listenForActorAndExecute(ObjectInputStream is) {
	try {
	    VacuumWorldEvent event = (VacuumWorldEvent) is.readObject();
	    VacuumWorldAbstractActionInterface action = event.getAction();
	    Result result = attemptExecution(action);
	    provideFeedback(result, getActorFromId(action.getActorID()));
	}
	catch(ClassNotFoundException | IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private void provideFeedback(Result result, Actor actor) {
	if(result instanceof VacuumWorldPhysicalActionResult) {
	    provideFeedback((VacuumWorldPhysicalActionResult) result, actor);
	}
	else if(result instanceof VacuumWorldCommunicativeActionResult) {
	    provideFeedback((VacuumWorldCommunicativeActionResult) result, actor);
	}
	else if(result instanceof VacuumWorldSensingActionResult) {
	    provideFeedback((VacuumWorldSensingActionResult) result, actor);
	}
	else {
	    throw new IllegalArgumentException();
	}
    }
    
    private void provideFeedback(VacuumWorldPhysicalActionResult result, Actor actor) {
	VacuumWorldPerception perception = buildPerception(result.getActionResultType(), actor);
	sendPerception(perception, actor);
    }
    
    private void provideFeedback(VacuumWorldCommunicativeActionResult result, Actor actor) {
	VacuumWorldSpeechPerception speechPerception = buildSpeechPerception(result.getMessage(), actor);
	VacuumWorldPerception perception = buildPerception(result.getActionResultType(), actor);
	sendPerception(perception, actor);
	sendSpeechPerception(speechPerception, getRecipientsFromIDs(result.getRecipients()));
    }
    
    private VacuumWorldSpeechPerception buildSpeechPerception(VacuumWorldMessage message, Actor actor) {
	return new VacuumWorldSpeechPerception(message, (VacuumWorldActorAppearance) ((AbstractAgent) actor).getAppearance());
    }

    private Set<Actor> getRecipientsFromIDs(Set<String> recipients) {
	return recipients.stream().map(this::getActorFromId).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private void provideFeedback(VacuumWorldSensingActionResult result, Actor actor) {
	VacuumWorldPerception perception = buildPerception(result.getActionResultType(), actor);
	sendPerception(perception, actor);
    }
    
    private VacuumWorldPerception buildPerception(ActionResult result, Actor actor) {
	VacuumWorldEnvironmentAppearance appearance = buildEnvironmentAppearance(actor);
	return new VacuumWorldPerception(result, appearance);
    }
    
    private VacuumWorldEnvironmentAppearance buildEnvironmentAppearance(Actor actor) {
	Orientation orientation = ((VacuumWorldActorAppearance) ((AbstractAgent) actor).getAppearance()).getOrientation();
	
	VacuumWorldLocation location = getLocationFromActorId(actor.getID());
	VacuumWorldLocation forward = this.grid.get(location.getCoordinates().getForwardCoordinates(orientation));
	VacuumWorldLocation left = this.grid.get(location.getCoordinates().getLeftCoordinates(orientation));
	VacuumWorldLocation right = this.grid.get(location.getCoordinates().getRightCoordinates(orientation));
	VacuumWorldLocation forwardLeft = this.grid.get(location.getCoordinates().getForwardLeftCoordinates(orientation));
	VacuumWorldLocation forwardRight = this.grid.get(location.getCoordinates().getForwardRightCoordinates(orientation));
	
	return buildEnvironmentAppearance(forward, left, right, forwardLeft, forwardRight);
    }

    private VacuumWorldEnvironmentAppearance buildEnvironmentAppearance(VacuumWorldLocation... locations) {
	Set<VacuumWorldLocation> nonNullLocations = Stream.of(locations).filter(Objects::nonNull).collect(Collectors.toSet());
	Map<VacuumWorldCoordinates, VacuumWorldLocation> locationsMap = new HashMap<>();
	nonNullLocations.forEach(location -> locationsMap.put(location.getCoordinates(), location));
	
	return new VacuumWorldEnvironmentAppearance(locationsMap);
    }

    private void sendPerception(VacuumWorldPerception perception, Actor actor) {
	sendGenericPerception(perception, actor.getID());
    }
    
    private void sendSpeechPerception(VacuumWorldSpeechPerception perception, Set<Actor> actors) {
	actors.stream().map(Actor::getID).forEach(id -> sendGenericPerception(perception, id));
    }
    
    private void sendGenericPerception(Perception perception, String recipientId) {
	try {
	    this.output.get(recipientId).writeObject(perception);
	    this.output.get(recipientId).flush();
	}
	catch(IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    private Result attemptExecution(VacuumWorldAbstractActionInterface action) {
	if(action instanceof VacuumWorldAbstractPhysicalAction) {
	    return ((VacuumWorldAbstractPhysicalAction) action).attempt(this, this.physics);
	}
	else if(action instanceof VacuumWorldAbstractCommunicativeAction) {
	    return ((VacuumWorldAbstractCommunicativeAction) action).attempt(this, this.physics);
	}
	else if(action instanceof VacuumWorldAbstractSensingAction) {
	    return ((VacuumWorldAbstractSensingAction) action).attempt(this, this.physics);
	}
	else {
	    throw new UnsupportedOperationException();
	}
    }
    
    public VacuumWorldLocation getLocationFromActorId(String id) {
	if(id == null) {
	    throw new IllegalArgumentException();
	}
	
	return this.grid.values().stream().filter(location -> location.containsSuchActor(id)).findFirst().orElse(null);
    }
    
    public Actor getActorFromId(String id) {
	if(id == null) {
	    throw new IllegalArgumentException();
	}
	
	return this.grid.values().stream().map(VacuumWorldLocation::getActorIfAny).filter(Objects::nonNull).filter(actor -> id.equals(actor.getID())).findFirst().orElse(null);
    }
    
    public void moveActor(String actorId) {
	VacuumWorldLocation location = getLocationFromActorId(actorId);	
	Actor actor = location.removeActor();
	VacuumWorldCoordinates original = location.getCoordinates();
	Orientation orientation = getOrientation(actor);
	
	if(!checkTargetLocation(original, orientation)) {
	    location.addActor(actor);
	    throw new UnsupportedOperationException("The target location already has an actor.");
	}
	else {
	    this.grid.get(original.getNeighborCoordinates(orientation)).addActor(actor);
	}
    }

    public boolean checkTargetLocation(VacuumWorldCoordinates original, Orientation orientation) {
	return !this.grid.get(original.getNeighborCoordinates(orientation)).containsAnActor();
    }
    
    public boolean checkOldLocation(VacuumWorldCoordinates old, String id) {
	return !this.grid.get(old).containsSuchActor(id);
    }

    public Orientation getOrientation(Actor actor) {
	if(actor == null) {
	    throw new IllegalArgumentException();
	}
	else if(actor instanceof AbstractAgent) {
	    return ((VacuumWorldActorAppearance) ((AbstractAgent) actor).getAppearance()).getOrientation();
	}
	
	throw new IllegalArgumentException();
    }

    @Override
    public void run() {
	while(!this.stopFlag) {
	    listenAndExecute();
	}
    }
}