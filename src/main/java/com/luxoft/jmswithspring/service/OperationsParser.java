package com.luxoft.jmswithspring.service;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OperationsParser {
    private static final Logger log = LoggerFactory.getLogger(OperationsParser.class);
    private static final String DATA_CORRUPTED = "Data corrupted: {}";

    @Autowired
    private Extractor extractor;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private SecurityMapper securityMapper;
    @Autowired
    private LineValidator validator;

    private List<String> list;

    public OperationsParser() {}

    public OperationsParser(List<String> list, String validLineLength) {
        this.list = list;
        this.validator = new LineValidator(validLineLength);
    }

    public List<User> parseUsers() {
        List<User> users = new LinkedList<>();

        list.forEach(line -> {
            try {
                validator.validate(line);
                String userStr = extractor.extractUser(line);
                User user = userMapper.map(userStr);
                users.add(user);
            } catch (CorruptedDataException e) {
                log.info("While parsing users - " + DATA_CORRUPTED, e.getMessage());
            }

        });

        return users;
    }

    public List<Transaction> parseTransactions() {
        List<Transaction> transactions = new LinkedList<>();

        list.forEach(line -> {
            try {
                validator.validate(line);
                String transactionStr = extractor.extractTransaction(line);
                Transaction transaction = transactionMapper.map(transactionStr);
                transactions.add(transaction);
            } catch (CorruptedDataException e) {
                log.info("While parsing transactions - " + DATA_CORRUPTED, e.getMessage());
            }
        });

        return transactions;
    }

    public List<Security> parseSecurities() {
        List<Security> securities = new LinkedList<>();

        list.forEach(line -> {
            try {
                validator.validate(line);
//                String securityStr = extractor.extractSecurities(line);
//                securityMapper.map(securityStr);
//                securities.add(security);
            } catch (CorruptedDataException e) {
                log.info("While parsing securities - " + DATA_CORRUPTED, e.getMessage());
            }
        });

        return securities;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
