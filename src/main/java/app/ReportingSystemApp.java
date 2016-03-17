package app;

import listeners.InConnection;
import listeners.MQListener;
import listeners.MQListenerImpl;
import listeners.MQListenerThread;

import javax.jms.JMSException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ReportingSystemApp {

    private static final String SLIS = "SLIS";
    private static final String XLIS = "XLIS";

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(new MQListenerThread(SLIS));
        pool.execute(new MQListenerThread(XLIS));


        Scanner scan = new Scanner(System.in);

        //wait until 'q' is pressed
        while(!"q".equals(scan.nextLine()));

        pool.shutdownNow();

        try {
            InConnection.getConnection().close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
