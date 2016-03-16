package app;

import listeners.MQListener;
import listeners.SLISListener;

import javax.jms.JMSException;

/**
 * Created by Prosner on 3/16/2016.
 *
 */

public class ReportingSystemApp {

    public static void main(String[] args) {
        String url = "tcp://localhost:61616";
        String SLIS = "SLIS";
        MQListener slisListener = new SLISListener(SLIS, url);
        try {
            slisListener.receiveMsg();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        System.out.println(slisListener.getMessage());
    }

}
