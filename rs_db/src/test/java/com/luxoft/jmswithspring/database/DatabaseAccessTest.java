package com.luxoft.jmswithspring.database;

import com.luxoft.jmswithspring.database.config.TestConfig;
import com.luxoft.jmswithspring.database.config.TestDAOConfig;
import com.luxoft.jmswithspring.database.dao.SuperDAO;
import com.luxoft.jmswithspring.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, TestDAOConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class DatabaseAccessTest {

    private static final String TABLE_USER = "USER";
    private static final String TABLE_TRANSACTION = "TRANSACTION";
    private static final String TABLE_BRANCH = "BRANCH";
    private static final String TABLE_SECURITY = "SECURITY";
    private static final String TABLE_LOT = "LOT";
    private static final String TABLE_SECURITIES_FOR_BRANCH = "SECURITIES_FOR_BRANCH";

    @Autowired
    private SuperDAO superDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldClearAllTables() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                TABLE_SECURITIES_FOR_BRANCH,
                TABLE_LOT,
                TABLE_SECURITY,
                TABLE_TRANSACTION,
                TABLE_BRANCH,
                TABLE_USER);

        assertEquals(INTEGER_ZERO.longValue(), countRowsInTable(jdbcTemplate, TABLE_USER));
        assertEquals(INTEGER_ZERO.longValue(), countRowsInTable(jdbcTemplate, TABLE_BRANCH));
        assertEquals(INTEGER_ZERO.longValue(), countRowsInTable(jdbcTemplate, TABLE_LOT));
        assertEquals(INTEGER_ZERO.longValue(), countRowsInTable(jdbcTemplate, TABLE_SECURITIES_FOR_BRANCH));
        assertEquals(INTEGER_ZERO.longValue(), countRowsInTable(jdbcTemplate, TABLE_SECURITY));
        assertEquals(INTEGER_ZERO.longValue(), countRowsInTable(jdbcTemplate, TABLE_TRANSACTION));
    }

    @Test
    public void shouldInsertTransactionToDatabase() {
        int rowsInTableUser = countRowsInTable(jdbcTemplate, TABLE_USER);
        int rowsInTableTransaction = countRowsInTable(jdbcTemplate, TABLE_TRANSACTION);
        int rowsInTableLot = countRowsInTable(jdbcTemplate, TABLE_LOT);
        int rowsInTableBranch = countRowsInTable(jdbcTemplate, TABLE_BRANCH);
        int rowsInTableSecurity = countRowsInTable(jdbcTemplate, TABLE_SECURITY);

        Transaction transaction = createTestTransaction();
        superDAO.safelyInsert(transaction);

        assertEquals(rowsInTableUser + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_USER));
        assertEquals(rowsInTableBranch + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_BRANCH));
        assertEquals(rowsInTableLot + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_LOT));
        assertEquals(rowsInTableSecurity + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_SECURITY));
        assertEquals(rowsInTableTransaction + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_TRANSACTION));
    }

    @Test
    public void transactionManagerShouldRollbackInvalidTransaction() {
        int rowsInTableUser = countRowsInTable(jdbcTemplate, TABLE_USER);
        int rowsInTableTransaction = countRowsInTable(jdbcTemplate, TABLE_TRANSACTION);
        int rowsInTableLot = countRowsInTable(jdbcTemplate, TABLE_LOT);
        int rowsInTableBranch = countRowsInTable(jdbcTemplate, TABLE_BRANCH);
        int rowsInTableSecurity = countRowsInTable(jdbcTemplate, TABLE_SECURITY);

        Transaction transaction = createInvalidTransaction();
        superDAO.safelyInsert(transaction);

        assertEquals(rowsInTableUser, countRowsInTable(jdbcTemplate, TABLE_USER));
        assertEquals(rowsInTableBranch, countRowsInTable(jdbcTemplate, TABLE_BRANCH));
        assertEquals(rowsInTableLot, countRowsInTable(jdbcTemplate, TABLE_LOT));
        assertEquals(rowsInTableSecurity, countRowsInTable(jdbcTemplate, TABLE_SECURITY));
        assertEquals(rowsInTableTransaction, countRowsInTable(jdbcTemplate, TABLE_TRANSACTION));
    }

    @Test
    public void shouldInsertSecuritiesForBranch() {
        int rowsInSecForBranch = countRowsInTable(jdbcTemplate, TABLE_SECURITIES_FOR_BRANCH);

        SecuritiesForBranches securitiesForBranches = createTestSecuritiesForBranches();

        superDAO.safelyInsert(securitiesForBranches);

        assertEquals(rowsInSecForBranch + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_SECURITIES_FOR_BRANCH));
    }

    @Test
    public void transactionManagerShouldRollbackInvalidSecuritiesForBranch() {
        int rowsInSecForBranch = countRowsInTable(jdbcTemplate, TABLE_SECURITIES_FOR_BRANCH);

        SecuritiesForBranches securitiesForBranches = createInvalidSecuritiesForBranch();

        superDAO.safelyInsert(securitiesForBranches);

        assertEquals(rowsInSecForBranch, countRowsInTable(jdbcTemplate, TABLE_SECURITIES_FOR_BRANCH));
    }

    private Transaction createTestTransaction() {
        return Transaction.builder()
                .withId(150)
                .withCountryCode(150)
                .withUser(User.builder()
                        .withUserId(3)
                        .withFirstName("Jan")
                        .withSurname("Nowak")
                        .withBirthDate("15/03/1987")
                        .build())
                .withOperationType(OperationType.MOVE_O)
                .withBranch(new Branch(54, "some address"))
                .withLots(new Lots().addLot(Lot.builder()
                        .withDescription("serious company ltd")
                        .withAmount(100).withDate("05/09/2014")
                        .withLotId(600)
                        .withPrice(BigDecimal.valueOf(500))
                        .withSecurityId(540)
                        .build()))
                .build();
    }

    private Transaction createInvalidTransaction() {
        return Transaction.builder()
                .withId(151)
                .withCountryCode(150)
                .withUser(User.builder()
                        .withUserId(4)
                        .withFirstName("Jan")
                        .withSurname("I'm absolutely, positively, double definitely, cross-my-heart, sure this will be to long to insert it into USER.NAME")
                        .withBirthDate("15/03/1987")
                        .build())
                .withOperationType(OperationType.MOVE_O)
                .withBranch(new Branch(54, "some address"))
                .withLots(new Lots().addLot(Lot.builder()
                        .withDescription("serious company ltd")
                        .withAmount(100).withDate("05/09/2014")
                        .withLotId(600).withPrice(BigDecimal.valueOf(500))
                        .withSecurityId(540)
                        .build()))
                .build();
    }

    private SecuritiesForBranches createTestSecuritiesForBranches() {
        SecuritiesForBranches securitiesForBranches = new SecuritiesForBranches();

        securitiesForBranches.setSecurities(Arrays.asList
                (Security.builder().withId(100).withAccessType(AccessType.ALLOW).withDate("05/05/2000").withSecurityId(500).build()));

        return securitiesForBranches;
    }

    private SecuritiesForBranches createInvalidSecuritiesForBranch() {
        SecuritiesForBranches securitiesForBranches = new SecuritiesForBranches();

        securitiesForBranches.setSecurities(Arrays.asList
                (Security.builder().withId(100).withAccessType(AccessType.ALLOW).withDate("it's not even a date").withSecurityId(500).build()));

        return securitiesForBranches;

    }
}
