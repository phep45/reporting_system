package com.luxoft.jmswithspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;

@Component
public class SLISReceiver {

    private static final Logger log = LoggerFactory.getLogger(SLISReceiver.class);

    @Autowired
    ConfigurableApplicationContext context;

    @JmsListener(destination = "slis", containerFactory = "dataJmsContainerFactory")
    public void receiveMessage(String message) {
        log.trace("SLIS: Received < {} >", message);
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

}
