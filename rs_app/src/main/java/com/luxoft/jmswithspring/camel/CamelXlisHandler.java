package com.luxoft.jmswithspring.camel;

import com.luxoft.jmswithspring.manager.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelXlisHandler implements CamelHandler {

    @Autowired
    private DataManager dataManager;

    @Override
    public void handle(String msg) {
        dataManager.processXLIS(msg);
    }
}
