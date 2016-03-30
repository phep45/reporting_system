package com.luxoft.jmswithspring.database.dao;


import com.luxoft.jmswithspring.model.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public abstract class GenericDAO<T> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public abstract void create(T t, int foreignKeyId);

    public void delete(int id, Class clazz) {
        TableName tableName = (TableName) clazz.getAnnotation(TableName.class);
        String sql = "DELETE FROM " + tableName.value() + " WHERE ID=" + id;
        jdbcTemplate.execute(sql);
    }

    public abstract void update(T t, int foreignKeyId);

    public abstract T get(int id);

    public abstract List<T> list();



}
