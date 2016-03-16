package listeners;

public class InvalidQueueNameException extends Exception {
    public InvalidQueueNameException(String msg) {
        super(msg);
    }
}
