package com.luxoft.jmswithspring.database;

import com.luxoft.jmswithspring.database.config.TestConfig;
import com.luxoft.jmswithspring.database.config.TestDAOConfig;
import com.luxoft.jmswithspring.database.dao.BaseDAO;
import com.luxoft.jmswithspring.model.Branch;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, TestDAOConfig.class},
loader = AnnotationConfigContextLoader.class)
public class BranchDAOTest {

    private static final String TABLE_BRANCH = "BRANCH";

    private static final int BRANCH_IN_DB_ID = 123;
    private static final String BRANCH_IN_DB_ADDRESS = "Scotland str. test 54";

    private static final int ID = 100;
    private static final String ADDRESS = "some address";

    private static final Branch BRANCH_IN_DB = new Branch(BRANCH_IN_DB_ID, BRANCH_IN_DB_ADDRESS);
    private static final String ADDRESS_LONGER_THAN_100_CHARACTERS =
            "this address is longer than one hundred characters so it will not be allowed in database. yes. it is true.";

    @Autowired
    private BaseDAO<Branch> dao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldInsertBranch() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_BRANCH);

        Branch newBranch = new Branch(ID, ADDRESS);

        dao.safelyInsert(newBranch);

        assertEquals(rows + INTEGER_ONE, countRowsInTable(jdbcTemplate, TABLE_BRANCH));
    }

    @Test
    public void shouldReturnBranch() {
        Branch branchFromDB = dao.get(BRANCH_IN_DB_ID);
        assertEquals(BRANCH_IN_DB, branchFromDB);
    }

    @Test
    public void shouldUpdateWhenIdExistsInDatabase() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_BRANCH);
        Branch newBranch = new Branch(BRANCH_IN_DB_ID, ADDRESS);
        dao.safelyInsert(newBranch);

        assertEquals(rows, countRowsInTable(jdbcTemplate, TABLE_BRANCH));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowException() {
        Branch invalidBranch = new Branch(ID, ADDRESS_LONGER_THAN_100_CHARACTERS);
        dao.safelyInsert(invalidBranch);
    }

}
