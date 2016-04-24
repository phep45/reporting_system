package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.database.dao.SuperDAO;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.service.slis.LineCollector;
import com.luxoft.jmswithspring.service.slis.SlisParser;
import com.luxoft.jmswithspring.service.xlis.SecurityXmlConverter;
import com.luxoft.jmswithspring.service.xlis.TransactionXmlConverter;
import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
@Component
public class DataManager {
    private static final Logger log = LoggerFactory.getLogger(DataManager.class);

    private static final String SEC_FOR_BRANCHES_REGEX = "<securities_branches>.*</securities_branches>";
    @Autowired
    private CamelContext camelContext;
    @Autowired
    private SlisParser slisParser;
    @Autowired
    private LineCollector lineCollector;
    @Autowired
    private TransactionXmlConverter transactionXmlConverter;
    @Autowired
    private SecurityXmlConverter securityXmlConverter;
    @Autowired
    private DateConverter dateConverter;
    @Autowired
    private SuperDAO superDAO;

    public void processSLIS(String slis) {
        List<String> listOfLines = lineCollector.collect(slis);

        List<Transaction> allOperations = new LinkedList<>();

        listOfLines.forEach(line -> {
            try {
                allOperations.add(slisParser.parse(line));
            } catch (CorruptedDataException e) {
                log.info("Data corrupted in line < {} >", line);
                log.info("CorruptedDataException", e);
            }
        });

        allOperations.forEach(superDAO::safelyInsert);

        log.info("SLIS processed successfully");
    }

    public void processXLIS(String xml) {
        //Check if XML contains transaction or securities for branch
        Pattern pattern = Pattern.compile(SEC_FOR_BRANCHES_REGEX, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(xml);

        if(matcher.find()) {
            processSecuritiesForBranches(xml);
        }else {
            processTransaction(xml);
        }

        log.info("XLIS processed successfully");
    }

    private void processTransaction(String xml) {
        Transaction transaction;
        try {
            transaction = transactionXmlConverter.unmarshal(xml);
            transaction.getLots().getListOfLots().forEach(lot -> {
                lot.setDate(dateConverter.usToIso(lot.getDate()));
            });

            superDAO.safelyInsert(transaction);

        } catch (CorruptedDataException e) {
            log.info("Corrupted data", e);
        }
    }

    private void processSecuritiesForBranches(String xml) {
        SecuritiesForBranches securitiesForBranches = securityXmlConverter.unmarshal(xml);
        superDAO.safelyInsert(securitiesForBranches);

    }

}
