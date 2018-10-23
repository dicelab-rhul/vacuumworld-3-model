package uk.ac.rhul.cs.dice.vacuumworld.environment;

import com.google.gson.JsonObject;

import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldActorAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;

public class VacuumWorldLocation implements VacuumWorldLocationInterface, Comparable<VacuumWorldLocation> {
    private static final long serialVersionUID = -4705357825465363219L;
    private VacuumWorldCoordinates coordinates;
    private VacuumWorldLocationAppearance appearance;
    private VacuumWorldCleaningAgent agent;
    private VacuumWorldUserAgent user;
    private VacuumWorldAvatar avatar;
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
	    return this.agent.getAppearance();
	}
	else if(this.user != null) {
	    return this.user.getAppearance();
	}
	else if(this.avatar != null) {
	    return this.avatar.getAppearance();
	}
	else {
	    return null;
	}
    }
    
    @Override
    public boolean containsAnActor() {
	return this.agent != null || this.user != null || this.avatar != null;
    }
    
    public boolean containsAnActorDifferentFrom(String id) {
	if(this.agent != null && this.agent.getID().equals(id)) {
	    return true;
	}
	else if(this.user != null && this.user.getID().equals(id)) {
	    return true;
	}
	else {
	    return this.avatar != null && this.avatar.getID().equals(id);
	}
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
    public boolean containsAnAvatar() {
	return this.avatar != null;
    }
    
    @Override
    public boolean containsCleaningAgentOfColor(AgentColor color) {
	if(color == null) {
	    throw new IllegalArgumentException();
	}
	
	return containsACleaningAgent() && color.equals(this.agent.getAppearance().getColor());
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
    public VacuumWorldAvatar getAvatarIfAny() {
	return this.avatar;
    }
    
    @Override
    public VacuumWorldDirt getDirtIfAny() {
	return this.dirt;
    }
    
    @Override
    public VacuumWorldActor getActorIfAny() {
	if(this.agent != null) {
	    return this.agent;
	}
	else if(this.user != null) {
	    return this.user;
	}
	else if(this.avatar != null) {
	    return this.avatar;
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
	    throw new UnsupportedOperationException(getOccupiedErrorMessage(getActiveBody(), getActiveBodyID()));
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
	    throw new UnsupportedOperationException(getOccupiedErrorMessage(getActiveBody(), getActiveBodyID()));
	}
	else {
	    this.user = user;
	    reviseAppearance();
	}
    }
    
    @Override
    public VacuumWorldAvatar removeAvatar() {
	VacuumWorldAvatar toReturn = new VacuumWorldAvatar(this.avatar);
	this.user = null;
	reviseAppearance();
	
	return toReturn;
    }
    
    @Override
    public void addAvatar(VacuumWorldAvatar avatar) {
	if(containsAnActor()) {
	    throw new UnsupportedOperationException(getOccupiedErrorMessage(getActiveBody(), getActiveBodyID()));
	}
	else {
	    this.avatar = avatar;
	    reviseAppearance();
	}
    }
    
    @Override
    public VacuumWorldActor removeActor() {
	if(this.agent != null) {
	    return removeAgent();
	}
	else if(this.user != null) {
	    return removeUser();
	}
	else if(this.avatar != null) {
	    return removeAvatar();
	}
	else {
	    return null;
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
    
    //I only care about the X value
    @Override
    public int compareTo(VacuumWorldLocation other) {
        return this.appearance.compareTo(other.getAppearance());
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	
	result = prime * result + ((this.appearance == null) ? 0 : this.appearance.hashCode());
	
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	return obj instanceof VacuumWorldLocation && this.appearance.equals(((VacuumWorldLocation) obj).getAppearance());
    }

    @Override
    public JsonObject serialize() {
	return this.appearance.serialize();
    }
    
    @Override
    public String toString() {
        return this.appearance.toString();
    }
    
    private String getOccupiedErrorMessage(String activeBody, String activeBodyId) {
	StringBuilder builder = new StringBuilder("The location at ");
	
	builder.append(this.coordinates);
	builder.append(" is already occupied by ");
	builder.append(activeBody);
	builder.append(activeBodyId);
	builder.append('.');
	
	return builder.toString();
    }

    private String getActiveBody() {
	if(containsACleaningAgent()) {
	    return "agent ";
	}
	else if(containsAUser()) {
	    return "user ";
	}
	else if(containsAnAvatar()) {
	    return "avatar ";
	}
	else {
	    return "unknown ";
	}
    }

    private String getActiveBodyID() {
	if(containsACleaningAgent()) {
	    return this.agent.getID();
	}
	else if(containsAUser()) {
	    return this.user.getID();
	}
	else if(containsAnAvatar()) {
	    return this.avatar.getID();
	}
	else {
	    return "UNKNOWN";
	}
    }
}