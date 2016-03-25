package com.luxoft.jmswithspring.database.user;

import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserJDBCTemplate implements UserDAO {
    private static final Logger log = LoggerFactory.getLogger(UserJDBCTemplate.class);
    
    private static final String INSERT_USER = "insert into User (id, name) values (?, ?)";
    private static final String SELECT_USER = "select * from User where id = ?";
    private static final String SELECT_ALL = "select * from User";
    private static final String DELETE_USER = "delete from User where id = ?";
    private static final String UPDATE_USER = "update User set name = ? where id = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(int id, String userName) {
        String sql = INSERT_USER;
        jdbcTemplate.update(sql, id, userName);
        log.info("Create: {}", sql);
    }

    @Override
    public User getUser(int id) {
        String sql = SELECT_USER;
        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBUserMapper());
        log.info("Get: {}", sql);
        return user;
    }

    @Override
    public List<User> listUsers() {
        String sql = SELECT_ALL;
        List<User> users = jdbcTemplate.query(sql, new DBUserMapper());
        log.info("List: {}", sql);
        return users;
    }

    @Override
    public void delete(int id) {
        String sql = DELETE_USER;
        jdbcTemplate.update(sql, id);
        log.info("Delete: {}", sql);
    }

    @Override
    public void update(int id, String userName) {
        String sql = UPDATE_USER;
        jdbcTemplate.update(sql, userName, id);
        log.info("Update: {}", sql);
    }
}
