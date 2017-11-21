package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;

import org.cloudstrife9999.logutilities.LogUtils;

public class VacuumWorld {

    private VacuumWorld() {}

    public static void main(String[] args) throws IOException {
	//String[] hostDetails = parseHostDetails(args);
	String[] hostDetails = new String[] { "127.0.0.1", "65000" };
	
	checkHostDetails(hostDetails[0], hostDetails[1]);
	
	//TODO these flags should become parameters.
	boolean fromFile = true;
	boolean simulatedRun = false;
	
	new VacuumWorldComponentsManager(fromFile, simulatedRun, hostDetails[0], Integer.valueOf(hostDetails[1]));
    }

    private static void checkHostDetails(String ip, String port) {
	if(ip == null || port == null) {
	    quitWithUsage();
	}
	
	if(!ip.matches("\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z")) {
	    quitWithUsage();
	}
	
	if(!testPort(port)) {
	    quitWithUsage();
	}
    }

    private static boolean testPort(String portRepresentation) {
	try {
	    int port = Integer.parseInt(portRepresentation);
	    
	    return port > 0 && port < 65536;
	}
	catch(NumberFormatException e) {
	    LogUtils.fakeLog(e);
	    
	    return false;
	}
    }

    private static String[] parseHostDetails(String[] args) {
	if(args.length < 4) {
	    return new String[] {null, null};
	}
	else {
	    return parseIpAndPort(args[0], args[1], args[2], args[3]);
	}
    }
    
    private static String[] parseIpAndPort(String ipFlag, String ip, String portFlag, String port) {
	return new String[] { parseIp(ipFlag, ip), parsePort(portFlag, port) };
    }

    private static String parseIp(String ipFlag, String ip) {
	return parseString("--ip", ipFlag, ip);
    }

    private static String parsePort(String portFlag, String port) {
	return parseString("--port", portFlag, port);
    }

    private static String parseString(String match, String flag, String candidate) {
	if(match.equals(flag)) {
	    return candidate;
	}
	else {
	    return null;
	}
    }

    private static void printUsage() {
	LogUtils.log("Usage: java -jar vw3.jar --ip <listening-ip> --port <listening-port>.");
    }
    
    private static void quitWithUsage() {
	printUsage();
	
	System.exit(-1);
    }
}