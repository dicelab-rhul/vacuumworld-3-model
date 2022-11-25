package uk.ac.rhul.cs.dice.vacuumworld.unitytests;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleSocketServer {
    private int serverPort;
    private ServerSocket serverSocket;
    private Map<String, SimpleTestRunnable> clients;

    public SimpleSocketServer(int port) {
        this.serverPort = port;
        this.clients = new HashMap<>();

        init();
    }

    private void init() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);

            manageClients();
        }
        catch (Exception e) {
            closeSocket();
        }
    }

    private void manageClients() {
        while (true) {
            try {
                Socket client = this.serverSocket.accept();
                String id = client.getInetAddress().toString();
                this.clients.put(id, new SimpleTestRunnable(id, new SimpleSocketClient(client)));

                Thread clientInterfaceThread = new Thread(this.clients.get(id));
                clientInterfaceThread.start();
            }
            catch (Exception e) {
                break;
            }
        }
    }

    public void closeSocket() {
        try {
            this.serverSocket.close();
        }
        catch (Exception e) {
            // Whatever
        }
    }

    public SimpleTestRunnable getClientInterfaceRunnable(String id) {
        return this.clients.get(id);
    }

    public String getHostname() {
        return this.serverSocket.getInetAddress().getHostAddress();
    }
}
