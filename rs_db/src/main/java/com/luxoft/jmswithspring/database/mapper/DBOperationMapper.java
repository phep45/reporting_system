package com.luxoft.jmswithspring.database.mapper;

import com.luxoft.jmswithspring.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperationMapper implements RowMapper<Operation> {

    private static final String COLUMN_LOT_ID = "id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_PRODUCT_ID = "product_id";

    private static final String COLUMN_TRANSACTION_ID = "id";
    private static final String COLUMN_OPERATION = "operation";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_BRANCH_ID = "branch_id";
    private static final String COLUMN_BRANCH_ADDRESS = "branch_address";

    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";

    private static final int SCALE = 5;

    @Override
    public Operation mapRow(ResultSet resultSet, int i) throws SQLException {

        int lotId = resultSet.getInt(COLUMN_LOT_ID);
        BigDecimal price = new BigDecimal(resultSet.getDouble(COLUMN_PRICE)).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        int amount = resultSet.getInt(COLUMN_AMOUNT);
        String date = resultSet.getString(COLUMN_DATE);
        int productId = resultSet.getInt(COLUMN_PRODUCT_ID);

        Security security = Security.builder()
                .withLotId(lotId)
                .withPrice(price)
                .withAmount(amount)
                .withDate(date)
                .withProductId(productId)
                .build();

        int id = resultSet.getInt(COLUMN_TRANSACTION_ID);
        OperationType operationType = OperationType.valueOf(resultSet.getString(COLUMN_OPERATION));
        String code = resultSet.getString(COLUMN_CODE);
        int branchId = resultSet.getInt(COLUMN_BRANCH_ID);
        String branchAddress = resultSet.getString(COLUMN_BRANCH_ADDRESS);

        Transaction transaction = Transaction.builder()
                .withId(id)
                .withOperationType(operationType)
                .withCountryCode(code)
                .withBranchId(branchId)
                .withBranchAddress(branchAddress)
                .build();

        int userId = resultSet.getInt(COLUMN_USER_ID);
        String name = resultSet.getString(COLUMN_USER_NAME);

        User user =  new User(id, name);

        Operation operation = new Operation();
        operation.setUser(user);
        operation.setTransaction(transaction);

        return null;
    }


}
