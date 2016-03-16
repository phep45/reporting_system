package app;

import listeners.MQListener;
import listeners.MQListenerImpl;

public class ReportingSystemApp {

    public static void main(String[] args) {
        MQListener slisListener = new MQListenerImpl("SLIS");

        slisListener.listen();

    }

}
