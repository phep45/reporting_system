package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.service.slis.LineCollector;
import com.luxoft.jmswithspring.service.slis.SlisParser;
import com.luxoft.jmswithspring.service.xlis.SecurityXmlConverter;
import com.luxoft.jmswithspring.service.xlis.TransactionXmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {
    private static final Logger log = LoggerFactory.getLogger(MessageProcessor.class);

    @Autowired
    private SlisParser slisParser;
    @Autowired
    private TransactionXmlConverter transactionXmlConverter;
    @Autowired
    private SecurityXmlConverter securityXmlConverter;
    @Autowired
    private DateConverter dateConverter;

    public Transaction processSlis(String slis) throws CorruptedDataException {
        log.info("Processing transaction String");
        return slisParser.parse(slis);
    }

    public SecuritiesForBranches processSecForBranchesXlis(String xlis) {
        log.info("Processing securities for branch XML");
        return securityXmlConverter.unmarshal(xlis);
    }

    public Transaction processTransactionXlis(String xml) throws CorruptedDataException {
        Transaction transaction;
        log.info("Processing transaction XML");
        transaction = transactionXmlConverter.unmarshal(xml);
        transaction.getLots().getListOfLots().forEach(lot -> {
            lot.setDate(dateConverter.usToIso(lot.getDate()));
        });

        return transaction;
    }

}
