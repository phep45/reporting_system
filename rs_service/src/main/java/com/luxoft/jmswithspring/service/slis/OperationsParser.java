package com.luxoft.jmswithspring.service.slis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Operation;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationsParser {
    private static final Logger log = LoggerFactory.getLogger(OperationsParser.class);

    @Autowired
    private Extractor extractor;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private SecurityMapper securityMapper;

    public Operation parse(String string) throws CorruptedDataException {
        log.info("Parsing string < {} >", string);
        Operation operation = new Operation();

        operation.setUser(parseUser(string));
        operation.setTransaction(parseTransactions(string));
        operation.setSecurities(parseSecurities(string));

        return operation;
    }

    private User parseUser(String line) throws CorruptedDataException {
        String userStr = extractor.extractUser(line);
        return userMapper.map(userStr);
    }

    private Transaction parseTransactions(String line) throws CorruptedDataException {
        String transactionStr = extractor.extractTransaction(line);
        return transactionMapper.map(transactionStr);

    }

    private List<Security> parseSecurities(String line) throws CorruptedDataException {
        String securitiesStr = extractor.extractSecurities(line);
        return securityMapper.map(securitiesStr);
    }

}
