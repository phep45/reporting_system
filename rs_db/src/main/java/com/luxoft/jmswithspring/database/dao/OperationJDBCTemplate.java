package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.database.mapper.DBLotMapper;
import com.luxoft.jmswithspring.database.mapper.DBUserMapper;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Lot;
import com.luxoft.jmswithspring.model.Operation;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OperationJDBCTemplate extends GenericDAO<Operation> {

    private static final Logger log = LoggerFactory.getLogger(OperationJDBCTemplate.class);

    @Autowired
    private GenericDAO<User> userDAO;

    @Autowired
    private GenericDAO<Transaction> transactionDAO;

    @Autowired
    private GenericDAO<Lot> securityDAO;

    public void create(Operation operation) throws CorruptedDataException {
        User user = operation.getUser();
        Transaction transaction = operation.getTransaction();
        List<Lot> securities = operation.getSecurities();

        if (existsInDatabase(user)) {
            addOperationToUser(user, transaction, securities);
            log.info("Operation added to user with id: {}", user.getUserId());
            return;
        }

        log.info("Creating new user");
        userDAO.create(user, 0);
        addToDatabase(user.getUserId(), transaction, securities);
    }

    private void addOperationToUser(User user, Transaction transaction, List<Lot> securities) throws CorruptedDataException {
        if (isCorrect(user)) {
            log.info("User with id: {} exists in database", user.getUserId());
            addToDatabase(user.getUserId(), transaction, securities);
            return;
        }
        throw new CorruptedDataException("User in DB with id " + user.getUserId() + " has different name than " + user);
    }

    private boolean isCorrect(User user) {
        User userFromDB = userDAO.get(user.getUserId());
        return userFromDB.equals(user);
    }

    private void addToDatabase(int userId, Transaction transaction, List<Lot> securities) {
        if (existsInDatabase(transaction)) {
            log.info("Transaction with id: {} exists in database");
            return;
        }
        transactionDAO.create(transaction, userId);
        log.info("New transaction added");

        securities.forEach(security -> {
            securityDAO.create(security, transaction.getId());
            log.info("New security added");
        });
    }

    private boolean existsInDatabase(Transaction transaction) {
        return transactionDAO.get(transaction.getId()) != null;
    }

    private boolean existsInDatabase(User user) {
        return userDAO.get(user.getUserId()) != null;
    }


    @Override
    public void create(Operation operation, int foreignKeyId) {
        try {
            create(operation);
        } catch (CorruptedDataException e) {
            log.info("", e);
        }
    }

    @Override
    public void update(Operation operation, int foreignKeyId) {

    }

    @Override
    public Operation get(int id) {
        return null;
    }

    @Override
    public List<Operation> list() {
        List<Operation> operations = new LinkedList<>();

        List<Transaction> transactions = transactionDAO.list();
        for (Transaction transaction : transactions) {
            Operation operation = new Operation();
            operation.setTransaction(transaction);
            int transactionId = transaction.getId();
            String sql = "SELECT * FROM SECURITY WHERE TRANSACTION_ID=?";
            List<Lot> securities = jdbcTemplate.query(sql, new Object[]{transactionId}, new DBLotMapper());
            operation.setSecurities(securities);

            sql = "SELECT ID,NAME FROM (select USER.ID, USER.NAME, TRANSACTION.ID AS TR_ID FROM USER LEFT JOIN TRANSACTION ON TRANSACTION.USER_ID=USER.ID) WHERE TR_ID=?";
            User user = jdbcTemplate.queryForObject(sql, new Object[]{transactionId}, new DBUserMapper());
            operation.setUser(user);

            operations.add(operation);
        }

        return operations;
    }
}
