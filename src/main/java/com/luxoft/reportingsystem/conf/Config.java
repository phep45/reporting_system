package com.luxoft.reportingsystem.conf;

import com.luxoft.reportingsystem.listeners.MQListener;
import com.luxoft.reportingsystem.listeners.MQListenerImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.jms.*;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

@Configuration
@ComponentScan("com.luxoft.reportingsystem.*")
public class Config {
    private static String URL = "tcp://localhost:61616";

    @Autowired
    private Connection connection;

    @Bean
    public MQListener sessionSlis() {
        return new MQListenerImpl("SLIS", getSession());
    }

    @Bean
    public MQListener sessionXlis() {
        return new MQListenerImpl("XLIS", getSession());
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
}
