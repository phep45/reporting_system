package app;

import listeners.MQListener;
import listeners.MQListenerImpl;

public class ReportingSystemApp {

    public static void main(String[] args) {
        String url = "tcp://localhost:61616";
        String SLIS = "SLIS";
        String XLIS = "XLIS";

        MQListener slisListener = new MQListenerImpl(SLIS);
        MQListener xlisListener = new MQListenerImpl(XLIS);

        slisListener.listen();
        xlisListener.listen();


    }

}
