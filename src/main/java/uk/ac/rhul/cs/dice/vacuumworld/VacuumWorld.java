package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.cloudstrife9999.logutilities.LogUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

public class VacuumWorld {
    private static final String HOST = "127.0.0.1";
    private static final String CONFIG_FILE_PATH = "config.json";
    public static final boolean DEBUGINFO = true; // info log flag
    public static final boolean DEBUG = false;
    public static final String DEBUG_CONFIGURATION = "easy.json";

    private VacuumWorld() {}

    public static void main(String[] args) throws IOException {
	String[] hostDetails = getHostDetails();
	
	if (!checkHostDetails(hostDetails[0], hostDetails[1])) {
	    LogUtils.log("Malformed or illegal details have been provided. Please edit " + CONFIG_FILE_PATH + " and retry.");
	    
	    return;
	}
	else {
	    startSystem(hostDetails[0], hostDetails[1]);
	}
    }

    private static String[] getHostDetails() {
	try {
	    JSONTokener tokener = new JSONTokener(new FileInputStream(CONFIG_FILE_PATH));
	    JSONObject root = new JSONObject(tokener);
	    
	    return new String[] {root.getString("model_port"), root.getString("environment_port")};
	}
	catch(FileNotFoundException e) {
	    LogUtils.fakeLog(e);
	    LogUtils.log(CONFIG_FILE_PATH + " was not found.");
	    
	    return new String[] {null, null};
	}
	catch(Exception e) {
	    LogUtils.fakeLog(e);
	    
	    return new String[] {null, null};
	}
    }

    private static void startSystem(String modelPort, String environmentPort) throws IOException {
	// TODO these flags should become parameters.
	boolean simulatedRun = false;
	
	if (!DEBUG) {
	    new VacuumWorldComponentsManager(simulatedRun, HOST, Integer.valueOf(modelPort), Integer.valueOf(environmentPort)).startUniverse();
	}
	else {
	    LogUtils.log("STARTING IN DEFAULT DEBUG MODE");
	    new VacuumWorldComponentsManager(DEBUG_CONFIGURATION, Integer.valueOf(modelPort), Integer.valueOf(environmentPort)).startUniverse();
	}
    }

    private static boolean checkHostDetails(String modelPort, String environmentPort) {
	if (modelPort == null || environmentPort == null) {
	    return false;
	}

	if (!testPort(modelPort)) {
	    return false;
	}

	if (!testPort(environmentPort)) {
	    return false;
	}

	return true;
    }

    private static boolean testPort(String portRepresentation) {
	try {
	    int port = Integer.parseInt(portRepresentation);

	    return port > 0 && port < 65536;
	}
	catch (NumberFormatException e) {
	    LogUtils.fakeLog(e);

	    return false;
	}
    }
}