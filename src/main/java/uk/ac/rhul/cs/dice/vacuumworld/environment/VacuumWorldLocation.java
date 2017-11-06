package uk.ac.rhul.cs.dice.vacuumworld.environment;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;

public class VacuumWorldLocation implements VacuumWorldLocationInterface {
    private VacuumWorldCoordinates coordinates;
    private VacuumWorldLocationAppearance appearance;
    private VacuumWorldCleaningAgent agent;
    private VacuumWorldUserAgent user;
    private VacuumWorldDirt dirt;
    private boolean[] walls; //N, S, W, E
    
    public VacuumWorldLocation(VacuumWorldCoordinates coordinates, boolean... walls) {
	this.coordinates = coordinates;
	this.walls = walls; //the integrity check is performed on reviseAppearance()
	
	reviseAppearance();
    }
    
    @Override
    public VacuumWorldCoordinates getCoordinates() {
	return this.coordinates;
    }
    
    @Override
    public VacuumWorldLocationAppearance getAppearance() {
	return this.appearance;
    }
    
    private void reviseAppearance() {
	this.appearance = new VacuumWorldLocationAppearance(this.coordinates, getActorAppearance(), getDirtAppearance(), this.walls);
    }

    private VacuumWorldDirtAppearance getDirtAppearance() {
	return this.dirt == null ? null : this.dirt.getAppearance();
    }

    private VacuumWorldActorAppearance getActorAppearance() {
	if(this.agent != null) {
	    return (VacuumWorldActorAppearance) this.agent.getAppearance();
	}
	else if(this.user != null) {
	    return (VacuumWorldActorAppearance) this.user.getAppearance();
	}
	else {
	    return null;
	}
    }

    @Override
    public boolean containsAnActor() {
	return this.agent != null || this.user != null;
    }
    
    @Override
    public boolean containsACleaningAgent() {
	return this.agent != null;
    }
    
    @Override
    public boolean containsAUser() {
	return this.user != null;
    }
    
    @Override
    public boolean containsSuchActor(String id) {
	return containsAnActor() && id.equals(getActorIfAny().getID());
    }
    
    @Override
    public boolean containsCleaningAgentOfColor(AgentColor color) {
	if(color == null) {
	    throw new IllegalArgumentException();
	}
	
	return containsACleaningAgent() && color.equals(((VacuumWorldActorAppearance) this.agent.getAppearance()).getColor());
    }
    
    @Override
    public boolean containsDirt() {
	return this.dirt != null;
    }
    
    @Override
    public boolean isCleanableBy(AgentColor color) {
	return containsDirt() && this.dirt.getAppearance().getColor().canBeCleanedBy(color);
    }
    
    @Override
    public boolean containsDirtOfColor(VacuumWorldDirtColor color) {
	if(color == null) {
	    throw new IllegalArgumentException();
	}
	
	return containsDirt() && color.equals(this.dirt.getAppearance().getColor());
    }
    
    @Override
    public VacuumWorldCleaningAgent getAgentIfAny() {
	return this.agent;
    }
    
    @Override
    public VacuumWorldUserAgent getUserIfAny() {
	return this.user;
    }
    
    @Override
    public VacuumWorldDirt getDirtIfAny() {
	return this.dirt;
    }
    
    @Override
    public Actor getActorIfAny() {
	if(this.agent != null) {
	    return this.agent;
	}
	else if(this.user != null) {
	    return this.user;
	}
	else {
	    return null;
	}
    }
    
    @Override
    public VacuumWorldCleaningAgent removeAgent() {
	VacuumWorldCleaningAgent toReturn = new VacuumWorldCleaningAgent(this.agent);
	this.agent = null;
	reviseAppearance();
	
	return toReturn;
    }
    
    @Override
    public void addAgent(VacuumWorldCleaningAgent agent) {
	if(containsAnActor()) {
	    throw new UnsupportedOperationException(getOccupiedErrorMessage(this.agent != null));
	}
	else {
	    this.agent = agent;
	    reviseAppearance();
	}
    }
    
    @Override
    public VacuumWorldUserAgent removeUser() {
	VacuumWorldUserAgent toReturn = new VacuumWorldUserAgent(this.user);
	this.user = null;
	reviseAppearance();
	
	return toReturn;
    }
    
    @Override
    public void addUser(VacuumWorldUserAgent user) {
	if(containsAnActor()) {
	    throw new UnsupportedOperationException(getOccupiedErrorMessage(this.agent != null));
	}
	else {
	    this.user = user;
	    reviseAppearance();
	}
    }
    
    @Override
    public Actor removeActor() {
	if(this.agent != null) {
	    return removeAgent();
	}
	else if(this.user != null) {
	    return removeUser();
	}
	else {
	    return null;
	}
    }
    
    @Override
    public void addActor(Actor actor) {
	if(actor instanceof VacuumWorldCleaningAgent) {
	    addAgent((VacuumWorldCleaningAgent) actor);
	}
	else if(actor instanceof VacuumWorldUserAgent) {
	    addUser((VacuumWorldUserAgent) actor);
	}
	else {
	    throw new IllegalArgumentException();
	}
    }
    
    @Override
    public VacuumWorldDirt removeDirt() {
	VacuumWorldDirt d = new VacuumWorldDirt(this.dirt);
	this.dirt = null;
	reviseAppearance();
	
	return d;
    }
    
    @Override
    public void addDirt(VacuumWorldDirt dirt) {
	if(this.dirt != null) {
	    throw new UnsupportedOperationException("There is already a " + this.dirt.getAppearance().getColor() + " dirt on " + this.coordinates + ".");
	}
	else {
	    this.dirt = dirt;
	    reviseAppearance();
	}
    }
    
    private String getOccupiedErrorMessage(boolean agent) {
	StringBuilder builder = new StringBuilder("The location at ");
	
	builder.append(this.coordinates);
	builder.append(" is already occupied by ");
	builder.append(agent ? "agent " : "user ");
	builder.append(agent ? this.agent.getID() : this.user.getID());
	builder.append('.');
	
	return builder.toString();
    }
}