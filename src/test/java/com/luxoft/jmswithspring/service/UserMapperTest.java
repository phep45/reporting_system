package com.luxoft.jmswithspring.service;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;
    private User expectedUser = new User(USER_ID, USER_NAME);

    private static final String INPUT = "00001     Stiven Meckalov";
    private static final int USER_ID = 1;
    private static final String USER_NAME = "Stiven Meckalov";

    private static final String TOO_SHORT_INPUT = "00001 Stiven Meckalov";
    private static final String CORRUPTED_INPUT = "A0001     Stiven Meckalov";

    @Before
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void shouldReturnUser() throws CorruptedDataException {
        User result = userMapper.map(INPUT);

        assertEquals(expectedUser, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowTooShortInput() throws CorruptedDataException {
        userMapper.map(TOO_SHORT_INPUT);
    }

    @Test(expected = CorruptedDataException.class)
    public void shouldThrowException() throws CorruptedDataException {
        userMapper.map(CORRUPTED_INPUT);
    }

}
