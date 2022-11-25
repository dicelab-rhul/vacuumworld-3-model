package uk.ac.rhul.cs.dice.vacuumworld.unitytests;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.serialization.ValidatingObjectInputStream;

public class SimpleSocketClient {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ValidatingObjectInputStream input;
    private ObjectOutputStream output;

    public SimpleSocketClient(String serverHostname, int serverPort) {
        try {
            this.socket = new Socket(serverHostname, serverPort);

            init();
        }
        catch (Exception e) {
            closeSocket();
        }
    }

    public SimpleSocketClient(Socket socket) {
        try {
            this.socket = socket;

            init();
        }
        catch (Exception e) {
            closeSocket();
        }
    }

    private void init() throws IOException {
        this.outputStream = this.socket.getOutputStream();
        this.inputStream = this.socket.getInputStream();
        this.input = new ValidatingObjectInputStream(this.inputStream);
        this.output = new ObjectOutputStream(this.outputStream);
    }

    public void closeSocket() {
        try {
            this.input.close();
            this.output.close();
            this.outputStream.close();
            this.inputStream.close();
            this.socket.close();
        }
        catch (Exception e) {
            // Whatever
        }
    }

    public void acceptClasses(Class<?>... classes) {
        this.input.accept(classes);
    }

    public void acceptPatterns(String... patterns) {
        this.input.accept(patterns);
    }

    public Socket getSocket() {
        return this.socket;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public ValidatingObjectInputStream getInput() {
        return this.input;
    }

    public ObjectOutputStream getOutput() {
        return this.output;
    }
}
