package com.luxoft.jmswithspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.FileSystemUtils;

import javax.jms.ConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
@EnableJms
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Bean
    public JmsListenerContainerFactory<?> dataJmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        return factory;
    }

    public static void main(String[] args) throws IOException {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        ConfigurableApplicationContext context = SpringApplication.run(App.class);

        MessageCreator messageCreator = session -> session.createTextMessage("ping");
        MessageCreator messageCreatorForXlis = session -> session.createTextMessage("<tag> ping </tag>");

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        log.trace("Sending a new message.");

        jmsTemplate.send("slis", messageCreator);
        jmsTemplate.send("slis", messageCreator);
//        jmsTemplate.send("xlis", messageCreatorForXlis);

        context.close();
    }
}
