package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.listener.XlisEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//@Component
public class XlisHandler implements ApplicationListener<XlisEvent> {

    @Autowired
    private DataManager dataManager;

    @Override
    public void onApplicationEvent(XlisEvent event) {
        dataManager.processXLIS((String) event.getSource());
    }
}
