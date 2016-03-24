package com.luxoft.reportingsystem.app;

import com.luxoft.reportingsystem.conf.Config;
import com.luxoft.reportingsystem.listeners.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ReportingSystemApp {
    private static final Logger log = LoggerFactory.getLogger(ReportingSystemApp.class);

    @Autowired
    @Qualifier("sessionSlis")
    private MQListener slisListenerThread;
    @Autowired
    @Qualifier("sessionXlis")
    private MQListener xlisListenerThread;
    @Autowired
    private Connection connection;

    public void run() {

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(() -> slisListenerThread.listen());
        pool.execute(() -> xlisListenerThread.listen());


        Scanner scan = new Scanner(System.in);

        //wait until 'q' is pressed
        while(!"q".equals(scan.nextLine()));

        pool.shutdownNow();

        try {
            connection.close();
        } catch (JMSException e) {
            log.error("Unable to close connection", e);
        }

    }


//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//        context.getBean(ReportingSystemApp.class).run();
//
//        context.close();
//    }
}
