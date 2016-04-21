package com.luxoft.jmswithspring.validator;

import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TransactionValidator implements Validator<Transaction> {
    private static final Logger log = LoggerFactory.getLogger(TransactionValidator.class);

    @Autowired
    private SecurityValidator securityValidator;

    @Override
    public boolean validate(Transaction transaction) {

        Security security = getSecurityFromTransaction(transaction);
        log.info("Validating securities: {} for Branch ID: {} in Transaction ID: {}", security.getSecurityIds(), transaction.getBranchId(), transaction.getId());
        return securityValidator.validate(security);
    }

    private Security getSecurityFromTransaction(Transaction transaction) {
        List<Integer> secIds = new LinkedList<>();
        transaction.getLots().getListOfLots().forEach(lot -> secIds.add(lot.getSecurityId()));

        Security security = new Security();
        security.setBranchId(transaction.getBranchId());
        security.setSecurityIds(secIds);
        return security;
    }
}

