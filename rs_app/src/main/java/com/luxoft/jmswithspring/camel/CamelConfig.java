package com.luxoft.jmswithspring.camel;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URI;

@Configuration
@ComponentScan
public class CamelConfig extends CamelConfiguration {

    @Autowired
    private ReportsRouteBuilder reportsRouteBuilder;


//    @Bean
//    public ActiveMQComponent activeMQComponent() {
//        ActiveMQComponent component = new ActiveMQComponent();
//        component.setBrokerURL("failover:tcp://localhost:61616");
//        return component;
//    }
//
//
//    @PostConstruct
//    public void brokerConfiguration() throws Exception {
//        BrokerService brokerService = new BrokerService();
//        TransportConnector transportConnector = new TransportConnector();
//        transportConnector.setUri(new URI("tcp://localhost:61616"));
//        brokerService.addConnector(transportConnector);
//        brokerService.setUseJmx(true);
//        brokerService.setPersistent(false);
//        brokerService.start();
//    }


}
