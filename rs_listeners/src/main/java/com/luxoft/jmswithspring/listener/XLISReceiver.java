package com.luxoft.jmswithspring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class XLISReceiver {

    private static final Logger log = LoggerFactory.getLogger(XLISReceiver.class);

    @Autowired
    ConfigurableApplicationContext context;

    @JmsListener(destination = "XLIS", containerFactory = "dataJmsContainerFactory")
    public void receiveMessage(String message) {
        log.info("XLIS: Received: {}", message);
//        System.out.println("XLIS: " + message);
//        context.close();
//        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
}