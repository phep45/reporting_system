package com.luxoft.reportingsystem.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MQListenerImpl implements MQListener {

    private ApplicationContext context;

    private static final int TIMEOUT = 1000;

    private List<Message> messages;

    @Autowired
    private Session session;

    private String mqName;

    public MQListenerImpl(){
        this(null);
    }

    public MQListenerImpl(String mqName) {
        this.mqName = mqName;
        this.messages = new ArrayList<Message>();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        if (session == null) {
            System.err.println("session failed");
            return;
        }
        Message msg;
        MessageConsumer consumer = null;
        try {
            Destination destination = session.createQueue(mqName);
            consumer = session.createConsumer(destination);
            while(true) {
                if(!Thread.interrupted()) {
                    msg = consumer.receive(TIMEOUT);
                    if (msg == null)
                        continue;
                    messages.add(msg);
                    System.out.println(mqName + ": " + ((TextMessage) msg).getText());
                }
                else break;
            }
        }catch (JMSException e) {
           if(e.getCause() instanceof InterruptedException) {
               System.out.println(mqName + " stopped");
           }
           else
               e.printStackTrace();
        }
        finally {
            try {
                if (consumer != null) {
                    consumer.close();
                }
                session.close();
//                InConnection.getConnection().close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMqName(String mqName) {
        this.mqName = mqName;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
