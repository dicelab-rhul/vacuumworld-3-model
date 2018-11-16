package uk.ac.rhul.cs.dice.vacuumworld.unitytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class ValidatingObjectInputStreamTest {
    
    @Testable
    @Test
    public void testWithString() {
	try {
	    final int port = 39393;
	    
	    SimpleSocketServer server = new SimpleSocketServer(port);
	    SimpleSocketClient client = new SimpleSocketClient(server.getHostname(), port);
	    
	    String testString = "test";
	    String id = client.getSocket().getInetAddress().toString();
	    
	    server.getClientInterfaceRunnable(id).getClientInterface().acceptClasses(String.class);
	    server.getClientInterfaceRunnable(id).getClientInterface().acceptPatterns("[C"); // Accept char
	    
	    client.getOutput().reset();
	    client.getOutput().writeObject(testString);
	    client.getOutput().flush();
	    
	    String received = (String) server.getClientInterfaceRunnable(id).getClientInterface().getInput().readObject();
	    
	    assertEquals(received, testString);
	    
	    client.closeSocket();
	    server.closeSocket();
	}
	catch (Exception e) {
	    fail("Got " + e);
	}
    }
    
    @Testable
    @Test
    public void testWithInteger() {
	try {
	    final int port = 39393;
	    
	    SimpleSocketServer server = new SimpleSocketServer(port);
	    SimpleSocketClient client = new SimpleSocketClient(server.getHostname(), port);
	    
	    Integer testInt = Integer.valueOf(42);
	    String id = client.getSocket().getInetAddress().toString();
	    
	    server.getClientInterfaceRunnable(id).getClientInterface().acceptClasses(Integer.class);
	    server.getClientInterfaceRunnable(id).getClientInterface().acceptPatterns("[I"); // Accept int
	    
	    client.getOutput().reset();
	    client.getOutput().writeObject(testInt);
	    client.getOutput().flush();
	    
	    Integer received = (Integer) server.getClientInterfaceRunnable(id).getClientInterface().getInput().readObject();
	    
	    assertEquals(received, testInt);
	    
	    client.closeSocket();
	    server.closeSocket();
	}
	catch (Exception e) {
	    fail("Got " + e);
	}
    }
}