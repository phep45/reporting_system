package listeners;

public class ListenersFactory {

    private static final String URL = "tcp://localhost:61616";

    private static ListenersFactory instance;

    private ListenersFactory() {

    }

    public static ListenersFactory instance() {
        if(instance != null)
            return instance;
        else {
            instance = new ListenersFactory();
            return instance;
        }

    }

    public MQListener createListener(String queueName) throws InvalidQueueNameException {
        if("SLIS".equals(queueName))
            return new SLISListener(queueName, URL);
        else if("XLIS".equals(queueName))
            return new XLISListener(queueName, URL);
        else {
            throw new InvalidQueueNameException("No queue named: " + queueName);
        }
    }

}
