package com.luxoft.jmswithspring.database.config;

import com.luxoft.jmswithspring.database.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource( value = {"classpath:jmswithspring/application_dev.properties"})
public class TestConfig {

    @Autowired
    private Environment env;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BranchDAO branchDAO;
    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private LotDAO lotDAO;
    @Autowired
    private SecuritiesForBranchesDAO securitiesForBranchesDAO;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("schema.sql", "data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    public SuperDAO superDAO() {
        SuperDAO superDAO = new SuperDAO();
        superDAO.setJdbcTemplate(jdbcTemplate());
        superDAO.setBranchDAO(branchDAO);
        superDAO.setLotDAO(lotDAO);
        superDAO.setSecuritiesForBranchesDAO(securitiesForBranchesDAO);
        superDAO.setUserDAO(userDAO);
        superDAO.setTransactionDAO(transactionDAO);
        superDAO.setTxManager(txManager());
        return superDAO;
    }

}
