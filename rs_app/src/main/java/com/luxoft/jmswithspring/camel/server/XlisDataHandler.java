package com.luxoft.jmswithspring.camel.server;

import com.luxoft.jmswithspring.manager.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "xlisDataHandler")
public class XlisDataHandler implements DataHandler {

    @Autowired
    private DataManager dataManager;

    @Override
    public void handle(String string) {
        dataManager.processXLIS(string);
    }
}
