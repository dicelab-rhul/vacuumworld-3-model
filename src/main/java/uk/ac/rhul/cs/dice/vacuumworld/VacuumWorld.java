package uk.ac.rhul.cs.dice.vacuumworld;

import java.io.IOException;
import java.net.InetAddress;

import org.cloudstrife9999.logutilities.LogUtils;

public class VacuumWorld {

	public static final boolean DEBUG = true;
	public static final String DEBUG_CONFIGURATION = "two_agents.json";

	private VacuumWorld() {
	}

	public static void main(String[] args) throws IOException {
		String[] hostDetails = getHostDetails(args);
		if (!checkHostDetails(hostDetails[0], hostDetails[1])) {
			return;
		} else {
			startSystem(hostDetails);
		}
	}

	private static String[] getHostDetails(String[] args) throws IOException {
		String[] hostDetails = parseHostDetails(args);

		for (String detail : hostDetails) {
			if (detail == null) {
				return new String[] { InetAddress.getLocalHost().getHostAddress(), "65000" };
			}
		}

		return hostDetails;
	}

	private static void startSystem(String[] hostDetails) throws IOException {
		// TODO these flags should become parameters.
		boolean simulatedRun = false;
		if (!DEBUG) {
			new VacuumWorldComponentsManager(simulatedRun, hostDetails[0], Integer.valueOf(hostDetails[1])).startUniverse();
		} else {
			LogUtils.log("STARTING IN DEFAULT DEBUG MODE");
			new VacuumWorldComponentsManager(DEBUG_CONFIGURATION, 65000).startUniverse();
		}
	}

	private static boolean checkHostDetails(String ip, String port) {
		if (ip == null || port == null) {
			return quitWithUsage();
		}

		if (!ip.matches("\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z")) {
			return quitWithUsage();
		}

		if (!testPort(port)) {
			return quitWithUsage();
		}

		return true;
	}

	private static boolean testPort(String portRepresentation) {
		try {
			int port = Integer.parseInt(portRepresentation);

			return port > 0 && port < 65536;
		} catch (NumberFormatException e) {
			LogUtils.fakeLog(e);

			return false;
		}
	}

	private static String[] parseHostDetails(String[] args) {
		if (args.length < 4) {
			return new String[] { null, null };
		} else {
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
		if (match.equals(flag)) {
			return candidate;
		} else {
			return null;
		}
	}

	private static void printUsage() {
		LogUtils.log("Usage: java -jar vw3.jar --ip <listening-ip> --port <listening-port>.");
	}

	private static boolean quitWithUsage() {
		printUsage();

		return false;
	}
}