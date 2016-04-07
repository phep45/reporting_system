package com.luxoft.jmswithspring.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseDAO<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public abstract void insert(T t);
    public abstract void safelyInsert(T t);
    public abstract T get(int id);
    public abstract void update(T t);
}
