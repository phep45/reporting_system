package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.database.dao.SuperDAO;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.service.slis.LineCollector;
import com.luxoft.jmswithspring.service.slis.SlisParser;
import com.luxoft.jmswithspring.service.xlis.TransactionXmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DataManager {
    private static final Logger log = LoggerFactory.getLogger(DataManager.class);

    @Autowired
    private SlisParser slisParser;
    @Autowired
    private LineCollector lineCollector;
    @Autowired
    private TransactionXmlConverter transactionXmlConverter;
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
    }

    public void processXLIS(String xml) {
        Transaction transaction = null;
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

}
