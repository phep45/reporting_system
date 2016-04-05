package com.luxoft.jmswithspring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Queue;

@Component
public class SLISReceiver implements ApplicationEventPublisherAware {

    private static final Logger log = LoggerFactory.getLogger(SLISReceiver.class);

    @Autowired
    private ConfigurableApplicationContext context;

    private ApplicationEventPublisher applicationEventPublisher;

    @JmsListener(destination = "SLIS", containerFactory = "dataJmsContainerFactory")
    public void receiveMessage(String message) {
        log.info("SLIS: Received < {} >", message);
        publish(message);
    }

    public void publish(String str) {
        SlisEvent slisEvent = new SlisEvent(str);
        applicationEventPublisher.publishEvent(slisEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
