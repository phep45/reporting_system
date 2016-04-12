package com.luxoft.jmswithspring.config;

import com.luxoft.jmswithspring.database.dao.TableCreator;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ComponentScan("com.luxoft.jmswithspring.*")
@PropertySource( value = {"classpath:jmswithspring/application_dev.properties"})
@SpringBootApplication
@EnableJms
@EnableTransactionManagement
public class OperationsConfig {

    @Autowired
    private Environment env;
    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JmsListenerContainerFactory<?> dataJmsContainerFactory() throws JMSException {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());

        return factory;
    }

    private ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(env.getProperty("mqserver.url"));
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @PostConstruct
    public void createTables() {
        TableCreator.createAll(new JdbcTemplate((dataSource)));
    }

//    @PostConstruct
    public void startBroker() throws Exception {
        BrokerService brokerService = new BrokerService();
        TransportConnector transportConnector = new TransportConnector();
        transportConnector.setUri(new URI(env.getProperty("mqserver.url")));
        brokerService.addConnector(transportConnector);
        brokerService.start();
    }

}