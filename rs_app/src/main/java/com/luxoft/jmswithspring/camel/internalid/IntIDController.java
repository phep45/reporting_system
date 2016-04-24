package com.luxoft.jmswithspring.camel.internalid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class IntIDController {

//    @Autowired
    private InternalId internalId;

    public IntIDController() {
        new Thread(() -> {
                new InternalIdAutoUpdater(internalId).schedule(5);
        }).start();
    }

    public void updateId() {
        internalId.increment();
    }

    public int checkCurrentId() {
        return internalId.getUniqueId();
    }

}
