package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationsDAO {
    private static final Logger log = LoggerFactory.getLogger(OperationsDAO.class);

    @Autowired
    private UserJDBCTemplate userJDBCTemplate;
    @Autowired
    private TransactionJDBCTemplate transactionJDBCTemplate;
    @Autowired
    private SecurityJDBCTemplate securityJDBCTemplate;


    public void create(Transaction transaction, int foreignKeyId) throws CorruptedDataException {

        User user = transaction.getUser();
        if(existsInDatabase(user)) {
            addTransactionToUser(transaction, user.getUserId());
            return;
        }
        userJDBCTemplate.create(user, user.getUserId());
        transactionJDBCTemplate.create(transaction.getId(), transaction.getType(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getBranchAddress(), transaction.getUser().getUserId());
        List<Security> securities = transaction.getLots().getListOfLots();
        securities.forEach(security -> securityJDBCTemplate.create(security, transaction.getId()));
    }

    private void addTransactionToUser(Transaction transaction, int userId) throws CorruptedDataException {
        if(isCorrect(transaction.getUser())) {
            addToDatabase(transaction, userId);
            return;
        }
        throw new CorruptedDataException("User in DB with id " + transaction.getUser().getUserId() + " has different name than " + transaction.getUser());
    }

    private void addToDatabase(Transaction transaction, int userId) {
        if(existsInDatabase(transaction)) {
            log.info("Transaction with id: {} exists in database. This transaction will be dropped.", transaction.getId());
            return;
        }
        transactionJDBCTemplate.create(transaction, userId);
        log.info("New transaction added");

        transaction.getLots().getListOfLots().forEach(security -> {
            securityJDBCTemplate.create(security, transaction.getId());
            log.info("New security added");
        });
    }

    private boolean isCorrect(User user) {
        User userFromDB = userJDBCTemplate.get(user.getUserId());
        System.out.println(userFromDB);
        System.out.println(user);
        return userFromDB.equals(user);
    }

    public void update(Transaction transaction, int foreignKeyId) {
        User user = transaction.getUser();
        userJDBCTemplate.update(user, user.getUserId());
        transactionJDBCTemplate.update(transaction, user.getUserId());
        List<Security> securities = transaction.getLots().getListOfLots();
        securities.forEach(security -> securityJDBCTemplate.update(security, transaction.getId()));
    }

    public Transaction get(int id) {
        return null;
    }

    public List<Transaction> list() {
        return null;
    }

    private boolean existsInDatabase(User user) {
        return userJDBCTemplate.get(user.getUserId()) != null;
    }

    private boolean existsInDatabase(Transaction transaction) {
        return transactionJDBCTemplate.get(transaction.getId()) != null;
    }
}
