package com.luxoft.reportingsystem.conf;

import com.luxoft.reportingsystem.listeners.InConnection;
import com.luxoft.reportingsystem.listeners.MQListenerThread;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;
import javax.jms.Session;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

@Configuration
@ComponentScan("com.luxoft.reportingsystem.listeners")
public class Config {


    @Bean
    public Session session() {
        try {
            InConnection.getConnection().start();
            return InConnection.getConnection().createSession(false, AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                InConnection.getConnection().close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Bean
    public MQListenerThread SLISListenerThread() {
        MQListenerThread listenerThread = new MQListenerThread();
        listenerThread.setMqName("SLIS");
        return listenerThread;
    }

    @Bean
    public MQListenerThread XLISListenerThread() {
        MQListenerThread listenerThread = new MQListenerThread();
        listenerThread.setMqName("XLIS");
        return listenerThread;
    }
}
