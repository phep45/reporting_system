package com.luxoft.reportingsystem.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class MQListenerImpl implements MQListener {
    private static final Logger log = LoggerFactory.getLogger(MQListenerImpl.class);

    private static final int TIMEOUT = 1000;

    private List<Message> messages = new ArrayList<Message>();

    private Session session;

    private String mqName;

    public MQListenerImpl(String mqName, Session session) {
        this.mqName = mqName;
        this.session = session;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        if (session == null) {
            log.error("Session failed");
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
                    log.trace("{} : {}", mqName, ((TextMessage) msg).getText());
                }
                else break;
            }
        }catch (JMSException e) {
           if(e.getCause() instanceof InterruptedException) {
               log.trace("{} stopped", mqName);
           }
           else
              log.error("Something went wrong: ", e);
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
