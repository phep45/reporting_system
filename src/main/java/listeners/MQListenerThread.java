package listeners;

public class MQListenerThread extends MQListenerImpl implements Runnable {

    public MQListenerThread(String mqName) {
        super(mqName);
    }

    public void run() {
        listen();
    }
}
