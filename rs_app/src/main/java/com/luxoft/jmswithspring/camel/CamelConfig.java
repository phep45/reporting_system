package com.luxoft.jmswithspring.camel;

import com.luxoft.jmswithspring.camel.handler.CamelSecForBranchHandler;
import com.luxoft.jmswithspring.camel.handler.CamelSlisHandler;
import com.luxoft.jmswithspring.camel.handler.CamelXlisHandler;
import com.luxoft.jmswithspring.manager.MessageProcessor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource( value = {"classpath:jmswithspring/application_dev.properties"})
public class CamelConfig extends CamelConfiguration {

    @Autowired
    private ReportsRouteBuilder reportsRouteBuilder;

    @Autowired
    private Environment env;
    @Autowired
    private MessageProcessor processor;
    @Autowired
    private DatabaseAccessor databaseAccessor;

    @Bean(name = "mqComponent")
    public JmsComponent jmsComponent() {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory());
        return jmsComponent;
    }



    private ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(env.getProperty("mqserver.url"));
    }


}
