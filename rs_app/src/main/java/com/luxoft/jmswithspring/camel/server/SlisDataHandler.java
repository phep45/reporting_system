package com.luxoft.jmswithspring.camel.server;

import com.luxoft.jmswithspring.manager.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "slisDataHandler")
public class SlisDataHandler implements DataHandler {

    @Autowired
    private DataManager dataManager;

    public void handle(final String string) {
        dataManager.processSLIS(string);
    }
}
