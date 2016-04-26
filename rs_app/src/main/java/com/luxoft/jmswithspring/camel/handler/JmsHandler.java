package com.luxoft.jmswithspring.camel.handler;

import org.apache.camel.Exchange;

public interface JmsHandler {
    void handle(Exchange msg);
}
