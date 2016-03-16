package app;

import listeners.MQListener;
import listeners.MQListenerImpl;

public class ReportingSystemApp {

    private static final String SLIS = "SLIS";

    public static void main(String[] args) {
        MQListener slisListener = new MQListenerImpl(SLIS);

        slisListener.listen();

    }

}
