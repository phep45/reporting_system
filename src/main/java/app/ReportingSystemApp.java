package app;

import listeners.ConsumersFactory;
import listeners.InvalidQueueNameException;
import listeners.MQListener;
import listeners.SLISListener;

public class ReportingSystemApp {

    public static void main(String[] args) {
        String url = "tcp://localhost:61616";
        String SLIS = "SLIS";
        String XLIS = "XLIS";
        MQListener slisListener = null;
        MQListener xlisListener = null;
        try {
            slisListener = ConsumersFactory.instance().createConsumer(SLIS);
            xlisListener = ConsumersFactory.instance().createConsumer(XLIS);
        } catch (InvalidQueueNameException e) {
            e.printStackTrace();
        }

        slisListener.listen();
        xlisListener.listen();
    }

}
