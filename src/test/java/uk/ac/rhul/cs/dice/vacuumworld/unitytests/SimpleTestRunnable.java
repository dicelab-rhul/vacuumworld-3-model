package uk.ac.rhul.cs.dice.vacuumworld.unitytests;

import org.cloudstrife9999.logutilities.LogUtils;

public class SimpleTestRunnable implements Runnable {
    private String id;
    private SimpleSocketClient clientInterface;

    public SimpleTestRunnable(String id, SimpleSocketClient clientInterface) {
        this.id = id;
        this.clientInterface = clientInterface;
    }

    @Override
    public void run() {
        long time = System.nanoTime();

        LogUtils.log(this.id + ": alive!");

        while (System.nanoTime() < time + 10000000000L) {
            continue;
        }

        LogUtils.log(this.id + ": bye!");
    }

    public String getID() {
        return this.id;
    }

    public SimpleSocketClient getClientInterface() {
        return this.clientInterface;
    }
}
