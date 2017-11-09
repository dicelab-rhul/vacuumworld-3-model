package uk.ac.rhul.cs.dice.vacuumworld.environment;

import uk.ac.rhul.cs.dice.agentcommon.interfaces.Actor;
import uk.ac.rhul.cs.dice.agentcontainers.interfaces.Location;
import uk.ac.rhul.cs.dice.vacuumworld.actors.AgentColor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldDirtColor;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldLocationAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.dirt.VacuumWorldDirt;

public interface VacuumWorldLocationInterface extends Location {
    public abstract VacuumWorldCoordinates getCoordinates();
    public abstract VacuumWorldLocationAppearance getAppearance();
    public abstract boolean containsAnActor();
    public abstract boolean containsACleaningAgent();
    public abstract boolean containsAUser();
    public abstract boolean containsAnAvatar();
    public abstract boolean containsSuchActor(String id);
    public abstract boolean containsCleaningAgentOfColor(AgentColor color);
    public abstract boolean containsDirt();
    public abstract boolean isCleanableBy(AgentColor color);
    public abstract boolean containsDirtOfColor(VacuumWorldDirtColor color);
    public abstract VacuumWorldCleaningAgent getAgentIfAny();
    public abstract VacuumWorldUserAgent getUserIfAny();
    public abstract VacuumWorldAvatar getAvatarIfAny();
    public abstract VacuumWorldDirt getDirtIfAny();
    public abstract Actor getActorIfAny();
    public abstract VacuumWorldCleaningAgent removeAgent();
    public abstract void addAgent(VacuumWorldCleaningAgent agent);
    public abstract VacuumWorldUserAgent removeUser();
    public abstract void addUser(VacuumWorldUserAgent user);
    public abstract VacuumWorldAvatar removeAvatar();
    public abstract void addAvatar(VacuumWorldAvatar avatar);
    public abstract Actor removeActor();    
    public abstract void addActor(Actor actor);
    public abstract VacuumWorldDirt removeDirt();
    public abstract void addDirt(VacuumWorldDirt dirt);
    
    public default boolean isEmpty() {
	return !containsAnActor() && !containsDirt();
    }
    
    public default boolean isNotEmpty() {
	return !isEmpty();
    }
}