package com.luxoft.jmswithspring.database.mapper;

import com.luxoft.jmswithspring.model.Lot;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBLotMapper implements RowMapper<Lot> {

    private static final String COLUMN_LOT_ID = "ID";
    private static final String COLUMN_PRICE = "PRICE";
    private static final String COLUMN_AMOUNT = "AMOUNT";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_PRODUCT_ID = "SEC_ID";
//    private static final String COLUMN_DES = "DESC";

    private static final int SCALE = 5;

    @Override
    public Lot mapRow(ResultSet resultSet, int i) throws SQLException {

        int lotId = resultSet.getInt(COLUMN_LOT_ID);
        BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(COLUMN_PRICE)).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        int amount = resultSet.getInt(COLUMN_AMOUNT);
        String date = resultSet.getString(COLUMN_DATE);
        int secId = resultSet.getInt(COLUMN_PRODUCT_ID);
//        String des = resultSet.getString(COLUMN_DES);

        return Lot.builder()
                .withLotId(lotId)
                .withPrice(price)
                .withAmount(amount)
                .withDate(date)
                .withSecurityId(secId)
//                .withDescription(des)
                .build();
    }
}
