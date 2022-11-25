package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.cloudstrife9999.logutilities.LogUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import uk.ac.rhul.cs.dice.vacuumworld.vwcommon.VWJSON;

public class VacuumWorld {
    private static final String CONFIG_FILE_PATH = "config.json";
    private static final boolean DEBUG = false; // remove this in future releases.
    private static final String DEBUG_CONFIGURATION = "easy.json";

    private VacuumWorld() {}

    public static void main(String[] args) throws IOException {
        LogUtils.enableVerbose();

        String[] hostDetails = getHostDetails(); // hostname, model port, environment port.

        if (!checkHostDetails(hostDetails[1], hostDetails[2])) {
            LogUtils.log("Malformed or illegal details have been provided. Please edit " + CONFIG_FILE_PATH + " and retry.");
        }
        else {
            startSystem(hostDetails[0], hostDetails[1], hostDetails[2]);
        }
    }

    private static String[] getHostDetails() {
        try {
            JSONTokener tokener = new JSONTokener(new FileInputStream(CONFIG_FILE_PATH));
            JSONObject root = new JSONObject(tokener);

            return new String[] { root.getString(VWJSON.MODEL_HOSTNAME), root.getString(VWJSON.MODEL_PORT), root.getString(VWJSON.ENVIRONMENT_PORT) };
        }
        catch (FileNotFoundException e) {
            LogUtils.fakeLog(e);
            LogUtils.log(CONFIG_FILE_PATH + " was not found.");

            return new String[] { null, null };
        }
        catch (Exception e) {
            LogUtils.fakeLog(e);

            return new String[] { null, null };
        }
    }

    private static void startSystem(String hostname, String modelPort, String environmentPort) throws IOException {
        if (!DEBUG) { // make this branch the only one in future releases.
            new VacuumWorldComponentsManager(hostname, Integer.valueOf(modelPort), Integer.valueOf(environmentPort)).startUniverse();
        }
        else { // remove this in future releases.
            LogUtils.log("STARTING IN DEFAULT DEBUG MODE");
            new VacuumWorldComponentsManager(DEBUG_CONFIGURATION, Integer.valueOf(modelPort), Integer.valueOf(environmentPort), true).startUniverse();
        }
    }

    private static boolean checkHostDetails(String modelPort, String environmentPort) {
        if (modelPort == null || environmentPort == null) {
            return false;
        }

        if (!testPort(modelPort)) {
            return false;
        }

        return testPort(environmentPort);
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
