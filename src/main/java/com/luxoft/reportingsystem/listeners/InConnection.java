package com.luxoft.reportingsystem.listeners;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class InConnection {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");

    private static String URL = (String)context.getBean("url");// = "tcp://localhost:61616";

    private static final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
    private static Connection connection;

    public static Connection getConnection() {
        if(connection != null)
            return connection;
        else {
            try {
                connection = connectionFactory.createConnection();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }

}
