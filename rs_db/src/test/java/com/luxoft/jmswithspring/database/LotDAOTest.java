package com.luxoft.jmswithspring.database;

import com.luxoft.jmswithspring.database.config.TestConfig;
import com.luxoft.jmswithspring.database.config.TestDAOConfig;
import com.luxoft.jmswithspring.database.dao.LotDAO;
import com.luxoft.jmswithspring.model.Lot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, TestDAOConfig.class},
loader = AnnotationConfigContextLoader.class)
public class LotDAOTest {

    private static final String TABLE_LOT = "LOT";
    private static final int LOT_ID = 100;
    private static final String DESCRIPTION = "serious company ltd.";
    private static final BigDecimal PRICE = BigDecimal.valueOf(0.0001);
    private static final int PRODUCT_ID = 999;
    private static final String DATE = "17/03/2016";
    private static final int AMOUNT = 1;
    private static final int TRANSACTION_IN_DB_ID = 120;

    private static final int LOT_IN_DB_ID = 123;
    private static final int LOT_IN_DB_SEC_ID = 655;
    private static final String LOT_IN_DB_DATE = "12/03/1999";
    private static final BigDecimal LOT_IN_DB_PRICE = BigDecimal.valueOf(500.21).setScale(5, BigDecimal.ROUND_HALF_UP);
    private static final int LOT_IN_DB_AMOUNT = 5;
    private static final String LOT_IN_DB_DESC = "PEPSI LTD";

    private static final Lot LOT_IN_DB = Lot.builder()
            .withLotId(LOT_IN_DB_ID)
            .withSecurityId(LOT_IN_DB_SEC_ID)
            .withDate(LOT_IN_DB_DATE)
            .withPrice(LOT_IN_DB_PRICE)
            .withAmount(LOT_IN_DB_AMOUNT)
            .withDescription(LOT_IN_DB_DESC)
            .build();

    private static final String INVALID_DATE = "can't believe it's not a date!";

    @Autowired
    private LotDAO dao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldInsertLot() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_LOT);
        Lot lot = Lot.builder()
                .withLotId(LOT_ID)
                .withDescription(DESCRIPTION)
                .withPrice(PRICE)
                .withSecurityId(PRODUCT_ID)
                .withDate(DATE)
                .withAmount(AMOUNT)
                .build();

        dao.safelyInsert(lot, TRANSACTION_IN_DB_ID);

        assertEquals(rows + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_LOT));
    }

    @Test
    public void shouldReturnLot() {
        Lot lotFromDB = dao.get(LOT_IN_DB_ID);
        assertEquals(LOT_IN_DB, lotFromDB);
    }

    @Test
    public void shouldUpdateWhenIdExistsInDatabase() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_LOT);
        dao.safelyInsert(LOT_IN_DB, TRANSACTION_IN_DB_ID);
        assertEquals(rows, countRowsInTable(jdbcTemplate, TABLE_LOT));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowException() {
        Lot invalidLot = Lot.builder()
                .withLotId(LOT_ID)
                .withDescription(DESCRIPTION)
                .withPrice(PRICE)
                .withSecurityId(PRODUCT_ID)
                .withDate(INVALID_DATE)
                .withAmount(AMOUNT)
                .build();
        dao.safelyInsert(invalidLot, TRANSACTION_IN_DB_ID);
    }
}
