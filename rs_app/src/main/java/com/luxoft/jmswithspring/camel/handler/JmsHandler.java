package com.luxoft.jmswithspring.camel.handler;

public interface JmsHandler<T> {
    T handle(String jmsMessage);
}
