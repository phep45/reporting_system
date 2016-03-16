package listeners;

public class ConsumersFactory {

    private static final String URL = "tcp://localhost:61616";

    private static ConsumersFactory instance;

    private ConsumersFactory() {

    }

    public static ConsumersFactory instance() {
        if(instance != null)
            return instance;
        else {
            instance = new ConsumersFactory();
            return instance;
        }

    }

    public MQListener createConsumer(String queueName) throws InvalidQueueNameException {
        if("SLIS".equals(queueName))
            return new SLISListener(queueName, URL);
        else if("XLIS".equals(queueName))
            return new XLISListener(queueName, URL);
        else {
            throw new InvalidQueueNameException("No queue named: " + queueName);
        }
    }

}
