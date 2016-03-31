package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBUserMapper;
import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserJDBCTemplate extends GenericDAO<User> {
    private static final Logger log = LoggerFactory.getLogger(UserJDBCTemplate.class);
    
    private static final String INSERT_USER = "insert into User (id, name, birth_date) values (?, ?, ?)";
    private static final String SELECT_USER = "select * from User where id = ?";
    private static final String SELECT_ALL = "select * from User";
    private static final String UPDATE_USER = "update User set name = ?, set birth_date = ? where id = ?";

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(int id, String userName, String birthDate) {
        String sql = INSERT_USER;
        jdbcTemplate.update(sql, id, userName, birthDate);
        log.info("Create: {}", sql);
    }

    public void create(User user, int id) {
        create(user.getUserId(), user.getUserName() + " " + user.getSurname(), user.getBirthDate());
    }

    public void update(User user, int id) {
        update(user.getUserId(), user.getUserName(), user.getBirthDate());
    }

    public User get(int id) {
        String sql = SELECT_USER;
        User user;
        try {
            user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBUserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        log.info("Get: {}", sql);
        return user;
    }

    public List<User> list() {
        String sql = SELECT_ALL;
        List<User> users = jdbcTemplate.query(sql, new DBUserMapper());
        log.info("List: {}", sql);
        return users;
    }


    public void update(int id, String userName, String birthDate) {
        String sql = UPDATE_USER;
        jdbcTemplate.update(sql, userName, birthDate, id);
        log.info("Update: {}", sql);
    }

}
