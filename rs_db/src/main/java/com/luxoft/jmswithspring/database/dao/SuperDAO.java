package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class SuperDAO {

    private static final Logger log = LoggerFactory.getLogger(SuperDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private BaseDAO<User> userDAO;
    @Autowired
    private BaseDAO<Transaction> transactionDAO;
    @Autowired
    private LotDAO lotDAO;
    @Autowired
    private BaseDAO<Branch> branchDAO;
    @Autowired
    private BaseDAO<SecuritiesForBranches> securitiesForBranchesDAO;

    //    @Transactional
    public void safelyInsert(Transaction transaction) {
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = txManager.getTransaction(definition);

        try {
            userDAO.safelyInsert(transaction.getUser());
            branchDAO.safelyInsert(transaction.getBranch());

            transactionDAO.safelyInsert(transaction);
            transaction.getLots().getListOfLots().forEach(lot -> {
                lotDAO.safelyInsertSecurity(lot);
                lotDAO.safelyInsert(lot, transaction.getId());
            });
            txManager.commit(status);
        } catch (DataAccessException e) {
            txManager.rollback(status);
            log.error("Transaction failed. Rollback.", e);
            return;
        }
        log.info("Whole transaction added to database.");
    }

    public void safelyInsert(SecuritiesForBranches securitiesForBranches) {
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = txManager.getTransaction(definition);

        try {
            securitiesForBranchesDAO.safelyInsert(securitiesForBranches);
            txManager.commit(status);
        } catch(DataAccessException e) {
            txManager.rollback(status);
            log.error("Transaction failed. Rollback.", e);
            return;
        }
        log.info("All securities for branch added to database.");
    }


}
