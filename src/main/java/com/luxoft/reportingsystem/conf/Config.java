package com.luxoft.reportingsystem.conf;

import com.luxoft.reportingsystem.collector.XLISCollector;
import com.luxoft.reportingsystem.listeners.MQListener;
import com.luxoft.reportingsystem.listeners.MQListenerImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.jms.*;

import java.util.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

@Configuration
@ComponentScan("com.luxoft.reportingsystem.*")
public class Config {
    private static String URL = "tcp://localhost:61616";

    private static Queue<Message> xlisQueue = new LinkedBlockingQueue<>();
    private static Queue<Message> slisQueue = new LinkedBlockingQueue<>();

    @Autowired
    private Connection connection;

    @Bean
    public MQListener sessionSlis() {
        return new MQListenerImpl("SLIS", getSession());
    }

    @Bean
    public MQListener sessionXlis() {
        MQListenerImpl xlis = new MQListenerImpl("XLIS", getSession());
        xlis.setMessageQueue(xlisQueue);
        return xlis;
    }

    private Session getSession() {
        try {
            connection.start();
            return connection.createSession(false, AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            try {
                connection.close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    @Bean
    public Connection connection() {
        try {
            return new ActiveMQConnectionFactory(URL).createConnection();
        } catch (JMSException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @Bean
    public XLISCollector xlisCollector() {
        XLISCollector xlisCollector = new XLISCollector();
        return  xlisCollector;
    }
}
