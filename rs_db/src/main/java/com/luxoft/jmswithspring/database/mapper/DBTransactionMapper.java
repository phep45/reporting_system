package com.luxoft.jmswithspring.database.mapper;

import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTransactionMapper implements RowMapper<Transaction> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OPERATION = "type";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_BRANCH_ID = "branch_id";
    private static final String COLUMN_USER_ID = "USER_ID";

    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt(COLUMN_ID);
        OperationType operationType = OperationType.valueOf(resultSet.getString(COLUMN_OPERATION));
        String code = resultSet.getString(COLUMN_CODE);
        int branchId = resultSet.getInt(COLUMN_BRANCH_ID);
        int userId = resultSet.getInt(COLUMN_USER_ID);

        return Transaction.builder()
                .withId(id)
                .withUser(User.builder().withUserId(userId).build())
                .withOperationType(operationType)
                .withCountryCode(code)
                .withBranchId(branchId)
                .build();
    }
}
