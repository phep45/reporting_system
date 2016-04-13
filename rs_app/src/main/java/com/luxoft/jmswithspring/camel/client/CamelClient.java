package com.luxoft.jmswithspring.camel.client;

import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelClient {

    public void run() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("camel-client.xml");
        ProducerTemplate camelTemplate = context.getBean("camelTemplate", ProducerTemplate.class);

        camelTemplate.sendBody("jms:queue:SLIS", ExchangePattern.InOut, "test");

        //TODO

        context.close();
    }

}
