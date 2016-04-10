package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SuperDAO {

    private static final Logger log = LoggerFactory.getLogger(SuperDAO.class);

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
    @Autowired
    private BaseDAO<SecuritiesForBranches> securitiesForBranchesDAO;

    @Transactional
    public void safelyInsert(Transaction transaction) {
        userDAO.safelyInsert(transaction.getUser());
        branchDAO.safelyInsert(transaction.getBranch());
        
        transactionDAO.safelyInsert(transaction);
        transaction.getLots().getListOfLots().forEach(lot -> {
            lotDAO.safelyInsertSecurity(lot);
            lotDAO.safelyInsert(lot, transaction.getId());
        });
        log.info("Whole transaction added to database.");
    }

    public void safelyInsert(SecuritiesForBranches securitiesForBranches) {
        securitiesForBranchesDAO.safelyInsert(securitiesForBranches);
        log.info("All securities for branch added to database.");
    }


}
