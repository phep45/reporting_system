package com.luxoft.jmswithspring.database.config;

import com.luxoft.jmswithspring.database.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TestDAOConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public BranchDAO branchDAO() {
        BranchDAO branchDAO = new BranchDAO();
        branchDAO.setJdbcTemplate(jdbcTemplate);
        return branchDAO;
    }

    @Bean
    public LotDAO lotDAO() {
        LotDAO lotDAO = new LotDAO();
        lotDAO.setJdbcTemplate(jdbcTemplate);
        return lotDAO;
    }

    @Bean
    public SecuritiesForBranchesDAO securitiesForBranchesDAO(){
        SecuritiesForBranchesDAO securitiesForBranchesDAO = new SecuritiesForBranchesDAO();
        securitiesForBranchesDAO.setJdbcTemplate(jdbcTemplate);
        return securitiesForBranchesDAO;
    }

    @Bean
    public TransactionDAO transactionDAO() {
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.setJdbcTemplate(jdbcTemplate);
        return transactionDAO;
    }

    @Bean
    public UserDAO userDAO() {
        UserDAO userDAO = new UserDAO();
        userDAO.setJdbcTemplate(jdbcTemplate);
        return userDAO;
    }



}
