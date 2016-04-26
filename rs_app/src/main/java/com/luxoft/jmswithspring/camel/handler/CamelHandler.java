package com.luxoft.jmswithspring.camel.handler;

import com.luxoft.jmswithspring.manager.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CamelHandler implements JmsHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MessageProcessor processor;

    public void setProcessor(MessageProcessor processor) {
        this.processor = processor;
    }
}
