package com.luxoft.jmswithspring.database;

import com.luxoft.jmswithspring.database.config.TestConfig;
import com.luxoft.jmswithspring.database.config.TestDAOConfig;
import com.luxoft.jmswithspring.database.dao.BaseDAO;
import com.luxoft.jmswithspring.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TransactionDAOTest {

    public static final String TABLE_TRANSACTION = "TRANSACTION";

    private static final int USER_IN_DB_ID = 12;

    public static final User USER_IN_DB = User.builder().withUserId(USER_IN_DB_ID).build();
    public static final int ID = 12;
    public static final int BRANCH_ID = 123;
    public static final OperationType TYPE = OperationType.MOVE_O;
    public static final int CODE = 130;
    public static final int TRANSACTION_IN_DB_ID = 999;
    public static final OperationType TYPE_IN_DB = OperationType.BUY;
    public static final String CODE_IN_DB = "US";
    public static final int BRANCH_ID_IN_DB = 123;

    @Autowired
    private BaseDAO<Transaction> dao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldInsertTransaction() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_TRANSACTION);

        Transaction transaction = Transaction.builder()
                .withId(ID)
                .withBranchId(BRANCH_ID)
                .withOperationType(TYPE)
                .withCountryCode(CODE)
                .withUser(USER_IN_DB)
                .build();

        dao.safelyInsert(transaction);

        assertEquals(rows + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_TRANSACTION));
    }

    @Test
    public void shouldReturnTransaction() {
        Transaction transactionFromDB = dao.get(TRANSACTION_IN_DB_ID);

        Transaction transactionInDB = buildTransactionAsIsInDB();

        assertEquals(transactionInDB, transactionFromDB);
    }

    private Transaction buildTransactionAsIsInDB() {
        return Transaction.builder()
                .withId(999)
                .withCountryCode("US")
                .withUser(User.builder()
                        .withUserId(12)
                        .withFirstName("Oleksii")
                        .withSurname("Fri")
                        .withBirthDate("05/03/2001")
                        .build())
                .withOperationType(OperationType.BUY)
                .withBranch(new Branch(500, "Scotland str. test 54"))

                .build();
    }
}
