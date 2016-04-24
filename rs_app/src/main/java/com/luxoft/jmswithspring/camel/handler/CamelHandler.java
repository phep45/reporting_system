package com.luxoft.jmswithspring.camel.handler;

import com.luxoft.jmswithspring.manager.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CamelHandler<T> implements JmsHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected MessageProcessor processor;

    public void setProcessor(MessageProcessor processor) {
        this.processor = processor;
    }
}
