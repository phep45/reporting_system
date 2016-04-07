package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.AccessType;
import com.luxoft.jmswithspring.model.Branch;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SuperDAO {

    private static final Logger log = LoggerFactory.getLogger(SuperDAO.class);

    private static final String INSERT_SEC_FOR_BRANCH = "INSERT INTO SECURITIES_FOR_BRANCH";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BaseDAO<User> userDAO;
    @Autowired
    private BaseDAO<Transaction> transactionDAO;
    @Autowired
    private LotDAO lotDAO;
    @Autowired
    private BaseDAO<Branch> branchDAO;

    @Transactional
    public void safelyInsert(Transaction transaction) {
        userDAO.safelyInsert(transaction.getUser());
        branchDAO.safelyInsert(transaction.getBranch());
        transactionDAO.safelyInsert(transaction);
        transaction.getLots().getListOfLots().forEach(lot -> {
            lotDAO.safelyInsertSecurity(lot);
            lotDAO.safelyInsert(lot, transaction.getId());
        });
    }

    public void insertSecurityForBranch(int branchId, int securityId, String date, AccessType accessType) {
        String sql = INSERT_SEC_FOR_BRANCH;
        jdbcTemplate.update(sql, branchId, securityId, date, accessType.toString());
        log.info("SEC_FOR_BRANCH inserted");
    }



}
