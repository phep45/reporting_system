package com.luxoft.jmswithspring.database.dao;

import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TransactionDAO extends GenericDAO<Transaction> {

    @Autowired
    private UserJDBCTemplate userJDBCTemplate;
    @Autowired
    private TransactionJDBCTemplate transactionJDBCTemplate;
    @Autowired
    private SecurityJDBCTemplate securityJDBCTemplate;

    @Override
    public void create(Transaction transaction, int foreignKeyId) {
        transactionJDBCTemplate.create(transaction.getId(), transaction.getType(), transaction.getCountryCode().toString(), transaction.getBranchId(), transaction.getBranchAddress(), transaction.getUser().getUserId());
        User user = transaction.getUser();
        userJDBCTemplate.create(user, user.getUserId());

        List<Security> securities = transaction.getLots().getListOfLots();

        securities.forEach(security -> securityJDBCTemplate.create(security, transaction.getId()));
    }

    @Override
    public void update(Transaction transaction, int foreignKeyId) {

    }

    @Override
    public Transaction get(int id) {
        return null;
    }

    @Override
    public List<Transaction> list() {
        return null;
    }
}
