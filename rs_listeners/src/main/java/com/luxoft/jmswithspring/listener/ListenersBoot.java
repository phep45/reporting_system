package com.luxoft.jmswithspring.listener;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.util.FileSystemUtils;

import javax.jms.JMSException;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

@SpringBootApplication
@EnableJms
public class ListenersBoot {

    private static final Logger log = LoggerFactory.getLogger(ListenersBoot.class);

    @Bean
    public Queue<String> slisQueue() {
        return new LinkedBlockingDeque<>();
    }

    @Bean
    public JmsListenerContainerFactory<?> dataJmsContainerFactory() throws JMSException {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());

        return factory;
    }

    private ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    public void run() {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        ConfigurableApplicationContext context = SpringApplication.run(ListenersBoot.class);
    }

//    public static void main(String[] args) throws IOException {
//        FileSystemUtils.deleteRecursively(new File("activemq-data"));
//        ConfigurableApplicationContext context = SpringApplication.run(Boot.class);
//
//        MessageCreator messageCreator = session -> session.createTextMessage("ping");
//        MessageCreator messageCreatorForXlis = session -> session.createTextMessage("<tag> ping </tag>");
//
//        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
//        log.info("Sending a new message.");
//
//        jmsTemplate.send("slis", messageCreator);
//        jmsTemplate.send("xlis", messageCreatorForXlis);
//
//        context.close();
//    }

//    @Bean
//    public ConfigurableApplicationContext slisConfigurableApplicationContext
}
