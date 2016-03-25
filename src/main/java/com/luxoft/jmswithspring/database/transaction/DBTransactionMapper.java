package com.luxoft.jmswithspring.database.transaction;

import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTransactionMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {

        int id = resultSet.getInt("id");
        OperationType operationType = OperationType.valueOf(resultSet.getString("operation"));
        String code = resultSet.getString("code");
        int branchId = resultSet.getInt("branch_id");

        return new Transaction(id, operationType, code, branchId);
    }
}
