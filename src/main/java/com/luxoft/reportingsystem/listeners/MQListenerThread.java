package com.luxoft.reportingsystem.listeners;

import org.springframework.stereotype.Component;

@Component
public class MQListenerThread extends MQListenerImpl implements Runnable {

    public MQListenerThread(){
        this(null);
    }

    public MQListenerThread(String mqName) {
        super(mqName);
    }

    public void run() {
        listen();
    }
}
