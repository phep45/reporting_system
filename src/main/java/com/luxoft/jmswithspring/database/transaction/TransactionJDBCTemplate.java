package com.luxoft.jmswithspring.database.transaction;

import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TransactionJDBCTemplate implements TransactionDAO {
    private static final Logger log = LoggerFactory.getLogger(TransactionJDBCTemplate.class);

    private static final String INSERT_TRANSACTION = "insert into Transaction (id, operation, code, branch_id, user_id) values (?, ?, ?, ?, ?)";
    private static final String SELECT_TRANSACTION = "select * from Transaction where id = ?";
    private static final String SELECT_ALL = "select * from Transaction";
    private static final String DELETE_TRANSACTION = "delete from Transaction where id = ?";
    private static final String UPDATE_TRANSACTION = "update Transaction set operation = ?, code = ?, branch_id =?, where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override
    public void create(int id, OperationType operationType, String code, int branchId, int userId) {
        String sql = INSERT_TRANSACTION;
        jdbcTemplate.update(sql, id, operationType, code, branchId, userId);
        log.info("Create: {}", sql);
    }

    @Override
    public Transaction getTransaction(int id) {
        String sql = SELECT_TRANSACTION;
        Transaction transaction = jdbcTemplate.queryForObject(sql, new Object[]{id}, new DBTransactionMapper());
        log.info("Get: {}", sql);
        return transaction;
    }

    @Override
    public List<Transaction> listTransactions() {
        String sql = SELECT_ALL;
        List<Transaction> transactions = jdbcTemplate.query(sql, new DBTransactionMapper());
        log.info("List: {}", sql);
        return transactions;
    }

    @Override
    public void delete(int id) {
        String sql = DELETE_TRANSACTION;
        jdbcTemplate.update(sql, id);
        log.info("Delete: {}", sql);
    }

    @Override
    public void update(int id, OperationType operationType, String code, int branchId, int userId) {
        String sql = UPDATE_TRANSACTION;
        jdbcTemplate.update(sql, operationType, code, branchId, userId, id);
        log.info("Update: {}", sql);
    }
}
