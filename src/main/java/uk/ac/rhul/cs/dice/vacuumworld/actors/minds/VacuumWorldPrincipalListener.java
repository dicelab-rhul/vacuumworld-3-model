package uk.ac.rhul.cs.dice.vacuumworld.actors.minds;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;

import uk.ac.rhul.cs.dice.agent.abstractimpl.AbstractPrincipalListener;
import uk.ac.rhul.cs.dice.agent.interfaces.Analyzable;
import uk.ac.rhul.cs.dice.agentcommon.interfaces.Action;

public class VacuumWorldPrincipalListener extends AbstractPrincipalListener {
    private static final long serialVersionUID = 587747917705356370L;

    public VacuumWorldPrincipalListener(ServerSocket server) throws IOException {
	super(server);
    }

    @Override
    public void perceive(Set<Analyzable> perceptions) {
	// TODO Auto-generated method stub

    }

    @Override
    public <T extends Action<?>> void execute(T action) {
	// TODO Auto-generated method stub

    }
}