package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.cloudstrife9999.logutilities.LogUtils;

import uk.ac.rhul.cs.dice.agentcontainers.abstractimpl.AbstractUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldActor;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldCleaningAgent;
import uk.ac.rhul.cs.dice.vacuumworld.actors.VacuumWorldUserAgent;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldUniverseAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.buildtasks.VacuumWorldEnvironmentBuilderTask;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldEnvironment;
import uk.ac.rhul.cs.dice.vacuumworld.environment.VacuumWorldLocation;
import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VacuumWorldRuntimeException;

public class VacuumWorldUniverse extends AbstractUniverse {
    private volatile boolean stop;

    /* 
     * FROM FILE.
     */
    public VacuumWorldUniverse(String hostname, int port) {
	super(null);

	createEnvironment(hostname, port);
	getAppearance().update(getEnvironment());

	bootUniverse(hostname, port);
    }

    /*
     * ONLINE.
     */
    public VacuumWorldUniverse(VacuumWorldEnvironment environment, String hostname, int port) {
	super(new VacuumWorldUniverseAppearance(environment), environment);

	bootUniverse(hostname, port);
    }

    @Override
    public VacuumWorldUniverseAppearance getAppearance() {
	return (VacuumWorldUniverseAppearance) super.getAppearance();
    }

    @Override
    public VacuumWorldEnvironment getMainAmbient() {
	return (VacuumWorldEnvironment) super.getMainAmbient();
    }

    public VacuumWorldEnvironment getEnvironment() {
	return getMainAmbient();
    }

    private void bootUniverse(String hostname, int port) {
	connectEnvironment();
	waitForEnvironment();
	connectComponents(hostname, port);

	this.stop = getEnvironment().getStopFlag();
    }

    private void waitForEnvironment() {
	boolean ready = false;
	
	while(!ready) {
	    ready = getEnvironment().canAcceptConnections();
	}
    }

    private void connectComponents(String hostname, int port) {
	getMainAmbient().setNumberOfExpectedActors(getAllActors().size());
	getAllActors().forEach(a -> connectToEnvironment(a, hostname, port));
	getMainAmbient().finishInitialization();
    }

    private void connectToEnvironment(VacuumWorldActor actor, String hostname, int port) {
	try {
	    actor.openSocket(hostname, port);
	}
	catch (IOException e) {
	    throw new VacuumWorldRuntimeException(e);
	}
    }

    //for debug
    private void createEnvironment(String hostname, int port) {
	addAmbient(new VacuumWorldEnvironment(VacuumWorldParser.parseConfiguration("easy.json"), this.stop, hostname, port, null, null));
    }

    private void connectEnvironment() {
	try {
	    LogUtils.log(this.getClass().getSimpleName() + ": creating environment...");

	    Thread t = new Thread(new VacuumWorldEnvironmentBuilderTask(getEnvironment()));
	    t.start();

	    LogUtils.log(this.getClass().getSimpleName() + ": environment socket OK.");
	}
	catch (Exception e) {
	    LogUtils.log(e);
	    Thread.currentThread().interrupt();
	}
    }

    public Set<VacuumWorldCleaningAgent> getAllCleaningAgents() {
	return getMainAmbient().getGrid().values().stream().filter(VacuumWorldLocation::containsACleaningAgent)
		.map(VacuumWorldLocation::getAgentIfAny).collect(Collectors.toSet());
    }

    public Set<VacuumWorldUserAgent> getAllUsers() {
	return getMainAmbient().getGrid().values().stream().filter(VacuumWorldLocation::containsAUser)
		.map(VacuumWorldLocation::getUserIfAny).collect(Collectors.toSet());
    }

    public Set<VacuumWorldAvatar> getAllAvatars() {
	return getMainAmbient().getGrid().values().stream().filter(VacuumWorldLocation::containsAnAvatar)
		.map(VacuumWorldLocation::getAvatarIfAny).collect(Collectors.toSet());
    }

    public Set<VacuumWorldActor> getAllActors() {
	return getMainAmbient().getGrid().values().stream().filter(VacuumWorldLocation::containsAnActor)
		.map(VacuumWorldLocation::getActorIfAny).collect(Collectors.toSet());
    }

    public int countActiveBodies() {
	return getMainAmbient().getAppearance().getAllLocationsWithActiveBodies().size();
    }
}