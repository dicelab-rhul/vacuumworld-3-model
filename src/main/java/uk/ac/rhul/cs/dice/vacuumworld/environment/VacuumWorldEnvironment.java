package uk.ac.rhul.cs.dice.vacuumworld.environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractAgent;
import uk.ac.rhul.cs.dice.agentactions.interfaces.Result;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractEnvironment;
import uk.ac.rhul.cs.dice.agentcontainers.enums.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.LogUtils;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldEvent;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractActionInterface;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractCommunicativeAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractPhysicalAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractSensingAction;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldEnvironmentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.environment.physics.VacuumWorldPhysics;

public class VacuumWorldEnvironment extends AbstractEnvironment {
    private static final int MINIMUM_SIZE = 3;
    private static final int MAXIMUM_SIZE = 10;
    private static final double LOADING_FACTOR = 0.75;
    private Map<VacuumWorldCoordinates, VacuumWorldLocation> grid;
    private int size;
    private VacuumWorldPhysics physics;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    public VacuumWorldEnvironment(int dimension) {
	int upperSize = dimension > MAXIMUM_SIZE ? MAXIMUM_SIZE : dimension;
	this.size = dimension < MINIMUM_SIZE ? MINIMUM_SIZE : upperSize;
	this.grid = new ConcurrentHashMap<>(1 + (int) (this.size * this.size / LOADING_FACTOR));
	this.physics = new VacuumWorldPhysics();
	
	initGrid();
	setAppearance(new VacuumWorldEnvironmentAppearance(this.grid));
    }
    
    public VacuumWorldEnvironment(Map<VacuumWorldCoordinates, VacuumWorldLocation> grid) {
	this.size = (int) Math.sqrt(grid.size());
	this.grid = new ConcurrentHashMap<>(grid);
	this.physics = new VacuumWorldPhysics();
	
	setAppearance(new VacuumWorldEnvironmentAppearance(this.grid));
    }

    private void initGrid() {
	for(int i = 0; i < this.size; i++) {
	    for(int j = 0; j < this.size; j++) {
		VacuumWorldCoordinates coordinates = new VacuumWorldCoordinates(i, j);
		this.grid.put(coordinates, new VacuumWorldLocation(coordinates, getWallInfo(i, j)));
	    }
	}
    }

    private boolean[] getWallInfo(int i, int j) {
	boolean[] walls = new boolean[4];
	
	walls[0] = j == 0; //north
	walls[1] = j == this.size - 1; //south
	walls[2] = i == 0; //west
	walls[3] = i == this.size - 1; //east
	
	return walls;
    }

    public Map<VacuumWorldCoordinates, VacuumWorldLocation> getGrid() {
	return ImmutableMap.copyOf(this.grid);
    }
    
    public synchronized void listenAndExecute() {
	try {
	    VacuumWorldEvent event = (VacuumWorldEvent) this.input.readObject();
	    VacuumWorldAbstractActionInterface action = event.getAction();
	    Result result = attemptExecution(action);
	    provideFeedback(result);
	}
	catch(ClassNotFoundException | IOException e) {
	    LogUtils.log(e);
	    provideFeedback();
	}
    }

    private void provideFeedback() {
	// TODO Auto-generated method stub
	
    }

    private void provideFeedback(Result result) {
	// TODO Auto-generated method stub
	
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
}