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
public class XLISReceiver {

    private static final Logger log = LoggerFactory.getLogger(XLISReceiver.class);

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    @Resource(name = "xlisQueue")
    private Queue<String> queue;

    @JmsListener(destination = "XLIS", containerFactory = "dataJmsContainerFactory")
    public void receiveMessage(String message) {
        log.info("XLIS received {} characters", message.length());
        queue.add(message);
    }
}