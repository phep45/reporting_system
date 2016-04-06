package com.luxoft.jmswithspring.database.mapper;

import com.luxoft.jmswithspring.model.Lot;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBLotMapper implements RowMapper<Lot> {

    private static final String COLUMN_LOT_ID = "id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final int SCALE = 5;

    @Override
    public Lot mapRow(ResultSet resultSet, int i) throws SQLException {

        int lotId = resultSet.getInt(COLUMN_LOT_ID);
        BigDecimal price = new BigDecimal(resultSet.getDouble(COLUMN_PRICE)).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        int amount = resultSet.getInt(COLUMN_AMOUNT);
        String date = resultSet.getString(COLUMN_DATE);
        int productId = resultSet.getInt(COLUMN_PRODUCT_ID);

        return Lot.builder()
                .withLotId(lotId)
                .withPrice(price)
                .withAmount(amount)
                .withDate(date)
                .withSecurityId(productId)
                .build();
    }
}
