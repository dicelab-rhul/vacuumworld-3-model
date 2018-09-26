package uk.ac.rhul.cs.dice.vacuumworld.exceptions;

public class VacuumWorldRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 9126615796611592354L;

    public VacuumWorldRuntimeException() {
	super();
    }

    public VacuumWorldRuntimeException(String message) {
	super(message);
    }

    public VacuumWorldRuntimeException(Throwable cause) {
	super(cause);
    }

    public VacuumWorldRuntimeException(String message, Throwable cause) {
	super(message, cause);
    }

    public VacuumWorldRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}