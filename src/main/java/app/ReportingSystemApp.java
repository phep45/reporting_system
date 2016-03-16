package app;

import listeners.ListenersFactory;
import listeners.InvalidQueueNameException;
import listeners.MQListener;

public class ReportingSystemApp {

    public static void main(String[] args) {
        String url = "tcp://localhost:61616";
        String SLIS = "SLIS";
        String XLIS = "XLIS";

        MQListener slisListener = null;
        MQListener xlisListener = null;

        ListenersFactory listenersFactory = ListenersFactory.instance();

        try {
            slisListener = listenersFactory.createListener(SLIS);
            xlisListener = listenersFactory.createListener(XLIS);
        } catch (InvalidQueueNameException e) {
            e.printStackTrace();
        }

        if(slisListener != null)
            slisListener.listen();

        if(xlisListener != null)
            xlisListener.listen();
    }

}
