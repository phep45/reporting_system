package com.luxoft.jmswithspring.database.mapper;

import com.luxoft.jmswithspring.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUserMapper implements RowMapper<User> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BIRTH_DATE = "birth_date";

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt(COLUMN_ID);
        String[] name = resultSet.getString(COLUMN_NAME).split(" ");
        String birthDate = resultSet.getString(COLUMN_BIRTH_DATE);

        return User.builder()
                .withUserId(id)
                .withFirstName(name[0])
                .withSurname(name[1])
                .withBirthDate(birthDate)
                .build();
    }
}
