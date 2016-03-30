package com.luxoft.jmswithspring.service;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

    private static final int ID_BEGIN = 0;
    private static final int ID_END = 5;
    private static final int NAME_BEGIN = 5;
    private static final int NAME_END = 25;
    private static final int VALID_LENGTH = 25;
    private static final String TWO_OR_MORE_SPACES = "\\s{2,}";

    public User map(String userAsString) throws CorruptedDataException {
        Preconditions.checkArgument(userAsString.length() == VALID_LENGTH, "Invalid input. String should be " + VALID_LENGTH + " characters long.");

        User user;
        try {
            int userId = Integer.parseInt(userAsString.substring(ID_BEGIN, ID_END).trim());
            String userName = userAsString.substring(NAME_BEGIN, NAME_END).trim().replaceAll(TWO_OR_MORE_SPACES, StringUtils.SPACE);
            user = new User(userId, userName);
        } catch (IllegalArgumentException e) {
            log.info("Data < {} > corrupted.", userAsString);
            throw new CorruptedDataException(e);
        }
        return user;
    }

}
