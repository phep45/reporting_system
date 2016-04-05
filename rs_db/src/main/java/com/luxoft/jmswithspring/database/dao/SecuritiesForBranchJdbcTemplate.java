package com.luxoft.jmswithspring.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SecuritiesForBranchJdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(SecuritiesForBranchJdbcTemplate.class);

    private static final String INSERT = "INSERT INTO SECURITIES_FOR_BRANCH (SEC_ID, BRANCH_ID) VALUES (?, ?)";
    private static final String DELETE_BY_SEC = "DELETE FROM SECURITIES_FOR_BRANCH WHERE SEC_ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(int securityId, int branchId) {
        String sql = INSERT;
        jdbcTemplate.update(sql, securityId, branchId);
        log.info("Create: {}", sql);
    }

    public void deleteBySecurity(int secId) {
        String sql = DELETE_BY_SEC;
        jdbcTemplate.update(sql, secId);
        log.info("Delete: {}", sql);
    }

}
