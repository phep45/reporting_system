package com.luxoft.jmswithspring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Queue;

@Component
public class SLISReceiver {

    private static final Logger log = LoggerFactory.getLogger(SLISReceiver.class);

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    @Resource(name="slisQueue")
    private Queue<String> queue;

    @JmsListener(destination = "SLIS", containerFactory = "dataJmsContainerFactory")
    public void receiveMessage(String message) {
        log.info("SLIS: Received < {} >", message);
        queue.add(message);
    }

}
