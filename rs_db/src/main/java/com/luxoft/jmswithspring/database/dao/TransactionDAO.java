package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBTransactionMapper;
import com.luxoft.jmswithspring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionDAO extends BaseDAO<Transaction> {

    private static final String INSERT_TRANSACTION = "INSERT INTO TRANSACTION (ID, TYPE, CODE, BRANCH_ID, USER_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_TRANSACTION = "SELECT * FROM TRANSACTION WHERE ID = ?";
    private static final String UPDATE_TRANSACTION = "UPDATE TRANSACTION SET TYPE = ?, CODE = ?, BRANCH_ID = ?, USER_ID = ? WHERE ID = ?";

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LotDAO lotDAO;
    @Autowired
    private BranchDAO branchDAO;

    public void safelyInsert(Transaction transaction) {
        if(get(transaction.getBranchId()) == null) {
            insert(transaction);
        }
        else {
            update(transaction);
        }
    }

    public void insert(Transaction transaction) {
        String sql = INSERT_TRANSACTION;
        jdbcTemplate.update(sql, transaction.getId(), transaction.getType().toString(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getUser().getUserId());
        log.info("TRANSACTION inserted");
    }

    public void update(Transaction transaction) {
        String sql = UPDATE_TRANSACTION;
        jdbcTemplate.update(sql, transaction.getType().toString(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getUser(), transaction.getId());
        log.info("TRANSACTION updated");
    }

    public Transaction get(int id) {
        String sql = SELECT_TRANSACTION;
        Transaction transaction;
        try {
            transaction = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBTransactionMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        log.info("TRANSACTION selected");
        User user = userDAO.get(transaction.getUser().getUserId());
        transaction.setUser(user);
        List<Lot> lotList = lotDAO.getLotByTranId(transaction.getId());
        Lots lots = new Lots();
        lots.setListOfLots(lotList);
        transaction.setLots(lots);
        Branch branch = branchDAO.get(transaction.getBranchId());
        transaction.setBranch(branch);
        return transaction;
    }

}
