package com.luxoft.jmswithspring.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TablesDAO {
    private static final Logger log = LoggerFactory.getLogger(TablesDAO.class);

    private static final String DROP_USER = "drop table if exists User;";
    private static final String CREATE_TABLE_USER = "create table User (id INT NOT NULL, name VARCHAR(20), UNIQUE KEY (id));";
    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE Transaction (id INT NOT NULL, operation VARCHAR(6), code VARCHAR(2), branch_id int, user_id int, UNIQUE KEY(id), FOREIGN KEY(user_id) REFERENCES User(id));";
    private static final String DROP_TRANSACTION = "DROP TABLE IF EXISTS Transaction;";
    private static final String DROP_SECURITY = "DROP TABLE IF EXISTS Security;";
    private static final String CREATE_TABLE_SECURITY = "CREATE TABLE Security (lot_id INT, price FLOAT, amount INT, date VARCHAR(10), product_id int, transaction_id int, FOREIGN KEY(transaction_id) REFERENCES Transaction(id));";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTableUser() {
        String sql = DROP_USER;
        jdbcTemplate.execute(sql);
        log.info("{}", sql);
        sql = CREATE_TABLE_USER;
        jdbcTemplate.execute(sql);
        log.info("Table User create: {}", sql);
    }

    public void createTableTransaction() {
        String sql = DROP_TRANSACTION;
        jdbcTemplate.execute(sql);
        log.info("{}", sql);
        sql = CREATE_TABLE_TRANSACTION;
        jdbcTemplate.execute(sql);
        log.info("Table Transaction created: {}", sql);
    }

    public void createTableSecurity() {
        String sql = DROP_SECURITY;
        jdbcTemplate.execute(sql);
        log.info("{}", sql);
        sql = CREATE_TABLE_SECURITY;
        jdbcTemplate.execute(sql);
        log.info("Table Security created: {}", sql);
    }

}
