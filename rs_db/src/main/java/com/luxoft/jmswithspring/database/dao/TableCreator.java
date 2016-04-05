package com.luxoft.jmswithspring.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.luxoft.jmswithspring.database.dao.TableCreatorConstants.*;

public enum TableCreator {

    USER(DROP_TABLE_USER, CREATE_TABLE_USER),
    TRANSACTION(DROP_TABLE_TRANSACTION, CREATE_TABLE_TRANSACTION),
    BRANCH(DROP_TABLE_BRANCH, CREATE_TABLE_BRANCH),
    SEC_FOR_BRANCH(DROP_TABLE_SEC_FOR_BRANCH, CREATE_TABLE_SEC_FOR_BRANCH),
    LOT(DROP_TABLE_LOT, CREATE_TABLE_LOT),
    SECURITY(DROP_TABLE_SECURITY, CREATE_TABLE_SECURITY);

    private static final Logger log = LoggerFactory.getLogger(TableCreator.class);

    private String drop;
    private String create;

    TableCreator(String drop, String create) {
        this.drop = drop;
        this.create = create;
    }

    public void createTable(JdbcTemplate jdbcTemplate) {
        String sql = drop;
        jdbcTemplate.execute(sql);
        sql = create;
        jdbcTemplate.execute(sql);
        log.info("Table " + this + " created.");
    }

    public static void createAll(JdbcTemplate jdbcTemplate) {
        for(TableCreator table : values())
            table.createTable(jdbcTemplate);
    }

}
