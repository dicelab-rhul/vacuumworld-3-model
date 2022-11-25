package uk.ac.rhul.cs.dice.vacuumworld.actors;

public enum ActorType {
    CLEANING_AGENT, USER, AVATAR;

    public char toChar() {
        switch (this) {
            case CLEANING_AGENT:
                return 'C';
            case USER:
                return 'U';
            case AVATAR:
                return 'A';
            default:
                throw new IllegalArgumentException();
        }
    }
}
