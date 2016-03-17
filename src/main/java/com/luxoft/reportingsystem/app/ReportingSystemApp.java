package com.luxoft.reportingsystem.app;

import com.luxoft.reportingsystem.conf.Config;
import com.luxoft.reportingsystem.listeners.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;
import javax.jms.Session;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportingSystemApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        MQListenerThread slisListenerThread = (MQListenerThread) context.getBean("SLISListenerThread");
        MQListenerThread xlisListenerThread = (MQListenerThread) context.getBean("XLISListenerThread");

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(slisListenerThread);
        pool.execute(xlisListenerThread);


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
