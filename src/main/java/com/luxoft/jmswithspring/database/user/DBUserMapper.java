package com.luxoft.jmswithspring.database.user;

import com.luxoft.jmswithspring.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");

        return new User(id, name);
    }
}
