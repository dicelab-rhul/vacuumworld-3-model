package uk.ac.rhul.cs.dice.vacuumworld.perception;

import uk.ac.rhul.cs.dice.agent.interfaces.Perception;

public class NothingMoreIncomingPerception implements Perception {
    private static final long serialVersionUID = 5341731631677759471L;
    private String info;

    public NothingMoreIncomingPerception() {
        this.info = "Nothing more incoming.";
    }

    public String getInfo() {
        return this.info;
    }
}
