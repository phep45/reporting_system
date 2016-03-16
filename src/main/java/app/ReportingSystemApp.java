package app;

import listeners.MQListener;
import listeners.SLISListener;

public class ReportingSystemApp {

    public static void main(String[] args) {
        String url = "tcp://localhost:61616";
        String SLIS = "SLIS";
        MQListener slisListener = new SLISListener(SLIS, url);
        slisListener.listen();
    }

}
