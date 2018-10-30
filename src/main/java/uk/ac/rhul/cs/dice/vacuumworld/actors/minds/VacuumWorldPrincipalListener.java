package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractPrincipalListener;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;

/**
 * 
 * DO NOT USE THIS CLASS IN THE COURSE-WORK!!!
 * 
 * @author cloudstrife9999
 *
 */
public class VacuumWorldPrincipalListener extends AbstractPrincipalListener {
    private static final long serialVersionUID = 587747917705356370L;

    public VacuumWorldPrincipalListener(ServerSocket server) throws IOException {
	super(server);
    }

    @Override
    public void perceive(Set<Analyzable> perceptions) {
	//useless for now.
    }

    @Override
    public <T extends Action<?>> void execute(T action) {
	//useless for now.
    }
    
    @Override
    public void revise() {
	//Edit here.
    }

    @Override
    public void receiveFirstPerception(Analyzable perception) {
	perceive(new HashSet<>(Arrays.asList(perception)));
    }
}