package com.luxoft.jmswithspring.service;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.model.User;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class UserMapper {

    private static final int ID_BEGIN = 0;
    private static final int ID_END = 5;
    private static final int NAME_BEGIN = 5;
    private static final int NAME_END = 25;
    private static final int VALID_LENGTH = 25;
    private static final String TWO_OR_MORE_SPACES = "\\s{2,}";

    public User map(String userAsString) {
        Preconditions.checkArgument(userAsString.length() == VALID_LENGTH, "Invalid input. String should be "+VALID_LENGTH+" characters long.");
        String userId = userAsString.substring(ID_BEGIN, ID_END).trim();
        String userName = userAsString.substring(NAME_BEGIN, NAME_END).trim().replaceAll(TWO_OR_MORE_SPACES, StringUtils.SPACE);

        return new User(new BigDecimal(userId), userName);
    }

}
