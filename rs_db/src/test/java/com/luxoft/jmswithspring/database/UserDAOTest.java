package com.luxoft.jmswithspring.database;

import com.luxoft.jmswithspring.database.config.TestConfig;
import com.luxoft.jmswithspring.database.config.TestDAOConfig;
import com.luxoft.jmswithspring.database.dao.BaseDAO;
import com.luxoft.jmswithspring.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, TestDAOConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class UserDAOTest {

    private static final String TABLE_USER = "USER";

    private static final int USER_IN_DB_ID = 12;
    private static final String USER_IN_DB_FIRST_NAME = "Oleksii";
    private static final String USER_IN_DB_SURNAME = "Fri";
    private static final String USER_IN_DB_BIRTH_DATE = "05/03/2001";

    private static final int USER_ID = 100;
    private static final String FIRST_NAME = "first";
    private static final String SURNAME = "surname";
    private static final String BIRTH_DATE = "04/05/1998";
    public static final String INVALID_DATE = "it's not even a date";

    @Autowired
    private BaseDAO<User> dao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private User TEST_USER = User.builder()
            .withUserId(USER_IN_DB_ID)
            .withFirstName(USER_IN_DB_FIRST_NAME)
            .withSurname(USER_IN_DB_SURNAME)
            .withBirthDate(USER_IN_DB_BIRTH_DATE)
            .build();

    @Test
    public void shouldInsertUserToDatabase() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_USER);

        User newUser = User.builder()
                .withUserId(USER_ID)
                .withFirstName(FIRST_NAME)
                .withSurname(SURNAME)
                .withBirthDate(BIRTH_DATE)
                .build();

        dao.safelyInsert(newUser);

        assertEquals(rows + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_USER));
    }

    @Test
    public void shouldReturnUser() {
        User userFromDB = dao.get(USER_IN_DB_ID);

        assertEquals(TEST_USER, userFromDB);
    }

    @Test
    public void shouldUpdateWhenIdExistsInDatabase(){
        int rows = countRowsInTable(jdbcTemplate, TABLE_USER);

        dao.safelyInsert(TEST_USER);

        assertEquals(rows, countRowsInTable(jdbcTemplate, TABLE_USER));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowException() {
        User invalidUser = User.builder().withUserId(USER_ID).withFirstName(FIRST_NAME).withSurname(SURNAME).withBirthDate(INVALID_DATE).build();
        dao.safelyInsert(invalidUser);
    }
}
