package uk.ac.rhul.cs.dice.vacuumworld.actors.minds.tutorials;

import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAbstractAction;
import uk.ac.rhul.cs.dice.vacuumworld.actors.minds.VacuumWorldAbstractMind;

/**
 * 
 * Mind for Tutorial 2.
 * 
 * @author cloudstrife9999, a.k.a. Emanuele Uliana
 *
 */
public class VacuumWorldTutorial2AgentMind extends VacuumWorldAbstractMind {
    // This is needed for serialization. Do not touch it.
    private static final long serialVersionUID = 8029125006026831040L;

    //Add any class attribute you want/need.

    /**
     * 
     * Constructor with the body ID.
     * 
     * @param bodyId the ID of this agent.
     * 
     */
    // Add any additional parameter you need to this constructor.
    public VacuumWorldTutorial2AgentMind(String bodyId) {
        super(bodyId);

        // Edit here if needed.
    }

    /**
     * 
     * This method is always automatically called after {@link #perceive()}, and before {@link #decide()}.<br/><br/>
     * 
     * Its intended use is to update the agent's beliefs.
     * 
     */
    @Override
    public void revise() {
        // Think of something useful to do here.
    }

    /**
     * 
     * This method is always automatically called after {@link #revise()}, and before {@link #execute()}.<br/><br/>
     * 
     * Its intended use is to output the next action for the agent to {@link #execute()}.
     * 
     */
    @Override
    public VacuumWorldAbstractAction decide() {
        // For students: amend this behaviour in order to make it meaningful.
        return decideWithRNG();
    }

    //Add any method you need.
}
