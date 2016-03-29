package com.luxoft.jmswithspring.database.transaction;

import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTransactionMapper implements RowMapper<Transaction> {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OPERATION = "operation";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_BRANCH_ID = "branch_id";

    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt(COLUMN_ID);
        OperationType operationType = OperationType.valueOf(resultSet.getString(COLUMN_OPERATION));
        String code = resultSet.getString(COLUMN_CODE);
        int branchId = resultSet.getInt(COLUMN_BRANCH_ID);

        return new Transaction(id, operationType, code, branchId);
    }
}
