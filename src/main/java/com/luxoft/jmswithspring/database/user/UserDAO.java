package com.luxoft.jmswithspring.database.user;

import com.luxoft.jmswithspring.model.User;

import javax.sql.DataSource;
import java.util.List;

public interface UserDAO {

    void setDataSource(DataSource dataSource);

    void create(int id, String userName);

    User getUser(int id);

    List<User> listUsers();

    void delete(int id);

    void update(int id, String userName);

}
