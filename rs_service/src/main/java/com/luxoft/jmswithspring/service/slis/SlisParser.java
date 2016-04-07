package com.luxoft.jmswithspring.service.slis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlisParser {
    private static final Logger log = LoggerFactory.getLogger(SlisParser.class);

    @Autowired
    private Extractor extractor;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private SecurityMapper securityMapper;

    public Transaction parse(String string) throws CorruptedDataException {
        log.info("Parsing string < {} >", string);
//        Operation operation = new Operation();

        Transaction transaction = parseTransactions(string);
        List<Lot> listOfLots = parseLots(string);
        Lots lots = new Lots();
        lots.setListOfLots(listOfLots);
        User user = parseUser(string);

        transaction.setUser(user);
        transaction.setLots(lots);

//        operation.setUser(parseUser(string));
//        operation.setTransaction(parseTransactions(string));
//        operation.setSecurities(parseLots(string));


        return transaction;
    }

    private User parseUser(String line) throws CorruptedDataException {
        String userStr = extractor.extractUser(line);
        return userMapper.map(userStr);
    }

    private Transaction parseTransactions(String line) throws CorruptedDataException {
        String transactionStr = extractor.extractTransaction(line);
        return transactionMapper.map(transactionStr);

    }

    private List<Lot> parseLots(String line) throws CorruptedDataException {
        String securitiesStr = extractor.extractSecurities(line);
        return securityMapper.map(securitiesStr);
    }

}
