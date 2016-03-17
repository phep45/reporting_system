package app;

import listeners.InConnection;
import listeners.MQListener;
import listeners.MQListenerImpl;

import javax.jms.JMSException;

public class ReportingSystemApp {

    private static final String SLIS = "SLIS";

    public static void main(String[] args) {
        MQListener slisListener = new MQListenerImpl(SLIS);

        slisListener.listen();
        try {
            InConnection.getConnection().close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
