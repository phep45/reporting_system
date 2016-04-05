package com.luxoft.jmswithspring.manager;


import com.luxoft.jmswithspring.listener.SlisEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SlisHandler implements ApplicationListener<SlisEvent> {

    @Autowired
    private DataManager dataManager;

    @Override
    public void onApplicationEvent(SlisEvent event) {
        dataManager.processSLIS((String) event.getSource());
    }
}
