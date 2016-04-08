package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBUserMapper;
import com.luxoft.jmswithspring.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class UserDAO extends BaseDAO<User> {

    private static final String INSERT_USER = "INSERT INTO USER (ID, NAME, BIRTH_DATE) VALUES (?, ?, ?)";
    private static final String SELECT_USER = "SELECT * FROM USER WHERE ID = ?";
    private static final String UPDATE_USER = "UPDATE USER SET NAME = ?, BIRTH_DATE = ? WHERE ID = ?";

    @Override
    public void safelyInsert(User user) {
        if(get(user.getUserId()) == null) {
            insert(user);
        }
        else {
            update(user);
        }
    }

    @Override
    public void insert(User user) {
        String sql = INSERT_USER;
        jdbcTemplate.update(sql, user.getUserId(), user.getUserName()+" "+user.getSurname(), user.getBirthDate());
        log.info("USER inserted");
    }

    @Override
    public void update(User user) {
        String sql = UPDATE_USER;
        jdbcTemplate.update(sql, user.getUserName()+" "+user.getSurname(), user.getBirthDate(), user.getUserId());
        log.info("USER updated");
    }

    @Override
    public User get(int id) {
        String sql = SELECT_USER;
        User user;
        try {
            user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBUserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("USER selected");
        return user;
    }

}
