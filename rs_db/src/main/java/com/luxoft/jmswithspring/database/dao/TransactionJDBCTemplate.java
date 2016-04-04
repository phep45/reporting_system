package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBTransactionMapper;
import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionJDBCTemplate extends GenericDAO<Transaction> {
    private static final Logger log = LoggerFactory.getLogger(TransactionJDBCTemplate.class);

    private static final String INSERT_TRANSACTION = "insert into Transaction (id, operation, code, branch_id, branch_address, user_id) values (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_TRANSACTION = "select * from Transaction where id = ?";
    private static final String SELECT_ALL = "select * from Transaction";
    private static final String DELETE_TRANSACTION = "delete from Transaction where id = ?";
    private static final String UPDATE_TRANSACTION = "update Transaction set operation = ?, code = ?, branch_id =?, branch_address = ?, where id = ?";

    public void create(int id, OperationType operationType, String code, int branchId, String branchAddress, int userId) {
        String sql = INSERT_TRANSACTION;
        jdbcTemplate.update(sql, id, operationType.toString(), code, branchId, branchAddress, userId);
        log.info("Create: {}", sql);
    }

    @Override
    public void create(Transaction transaction, int userId) {
        create(transaction.getId(), transaction.getType(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getBranchAddress(), userId);
    }

    @Override
    public void update(Transaction transaction, int foreignKeyId) {
        update(transaction.getId(), transaction.getType(), transaction.getCountryCode().toString(), transaction.getBranchId(), foreignKeyId);
    }

    @Override
    public Transaction get(int id) {
        String sql = SELECT_TRANSACTION;
        Transaction transaction;
        try {
            transaction = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBTransactionMapper());
            log.info("Get: {}", sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return transaction;
    }

    @Override
    public List<Transaction> list() {
        String sql = SELECT_ALL;
        List<Transaction> transactions = jdbcTemplate.query(sql, new DBTransactionMapper());
        log.info("List: {}", sql);
        return transactions;
    }

    public void update(int id, OperationType operationType, String code, int branchId, int userId) {
        String sql = UPDATE_TRANSACTION;
        jdbcTemplate.update(sql, operationType, code, branchId, userId, id);
        log.info("Update: {}", sql);
    }
}
