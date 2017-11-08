package uk.ac.rhul.cs.dice.vacuumworld;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
    private static final Logger LOGGER = initLogger(true);
    private static final Logger ERRORS_LOGGER = initLogger(false);

    private LogUtils() {
    }

    private static Logger initLogger(boolean stdout) {
	Logger logger = Logger.getGlobal();
	logger.setUseParentHandlers(false);
	ConsoleHandler handler = stdout ? new VacuumWorldConsoleHandler() : new ConsoleHandler();
	handler.setFormatter(new VacuumWorldLogFormatter());
	logger.addHandler(handler);

	return logger;
    }

    public static <T> void log(T message) {
	log(Level.INFO, message.toString());
    }
    
    public static void log(String message) {
	log(Level.INFO, message);
    }

    public static void log(Exception e) {
	if (e.getMessage() != null) {
	    log(e.getMessage(), e);
	} else {
	    log(e.getClass().getSimpleName(), e);
	}
    }

    public static void log(Exception e, String className) {
	if (e.getMessage() != null) {
	    log(className + ": " + e.getMessage(), e);
	} else {
	    log(className + ": " + e.getClass().getSimpleName(), e);
	}
    }

    public static void fakeLog(Exception e) {
	// this exception does not need to be logged
    }

    public static void log(String message, Exception e) {
	log(Level.SEVERE, e.getClass().getCanonicalName() + ": " + message, e);
    }

    public static void log(Level level, String message) {
	LOGGER.log(level, message);
    }

    public static void log(Level level, String message, Exception e) {
	ERRORS_LOGGER.log(level, message, e);
    }

    public static void logWithClass(String source, String message) {
	log(source + ": " + message);
    }

    public static void logState(String s) {
	log("\n\n" + s + "\n\n");
    }
}