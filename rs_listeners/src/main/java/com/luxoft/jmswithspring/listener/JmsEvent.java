package com.luxoft.jmswithspring.listener;

import org.springframework.context.ApplicationEvent;

public abstract class JmsEvent extends ApplicationEvent {

    public JmsEvent(Object source) {
        super(source);
    }

}
