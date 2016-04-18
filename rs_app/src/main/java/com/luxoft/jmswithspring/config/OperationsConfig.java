package com.luxoft.jmswithspring.config;

import com.luxoft.jmswithspring.camel.CamelConfig;
import com.luxoft.jmswithspring.database.dao.TableCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.luxoft.jmswithspring.*")
@PropertySource( value = {"classpath:jmswithspring/application_dev.properties"})
@SpringBootApplication
//@EnableJms
@EnableTransactionManagement
@Import(CamelConfig.class)
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

    //    @Bean
//    public JmsListenerContainerFactory<?> dataJmsContainerFactory() throws JMSException {
//        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//
//        return factory;


    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

//    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @PostConstruct
    public void createTables() throws Exception {
        TableCreator.createAll(new JdbcTemplate((dataSource)));


    }


}