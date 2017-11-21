package uk.ac.rhul.cs.dice.vacuumworld.exceptions;

public class VacuumWorldException extends Exception {
    private static final long serialVersionUID = -4984847693714446958L;

    public VacuumWorldException() {}

    public VacuumWorldException(String message) {
	super(message);
    }

    public VacuumWorldException(Throwable cause) {
	super(cause);
    }

    public VacuumWorldException(String message, Throwable cause) {
	super(message, cause);
    }

    public VacuumWorldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}