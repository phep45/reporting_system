package com.luxoft.jmswithspring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@ComponentScan("com.luxoft.jmswithspring.*")
public class OperationsConfig {
    @Value("${jdbc.driverClassName")
    private String driverClassname;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username")
    private String userName;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[] {
                new ClassPathResource("/src/resources/jmswithspring/database.properties")
        };

        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);

        return ppc;
    }


    @Bean(name = "datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassname);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

}
