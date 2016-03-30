package com.luxoft.jmswithspring.manager;

import com.luxoft.jmswithspring.database.dao.GenericDAO;
import com.luxoft.jmswithspring.database.dao.TablesDAO;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Operation;
import com.luxoft.jmswithspring.service.LineCollector;
import com.luxoft.jmswithspring.service.OperationsParser;
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
    private TablesDAO tablesDAO;
    @Autowired
    private OperationsParser operationsParser;
    @Autowired
    private LineCollector lineCollector;
    @Autowired
    private GenericDAO<Operation> operationDAO;

    public void createDatabase() {
        tablesDAO.createTableUser();
        tablesDAO.createTableTransaction();
        tablesDAO.createTableSecurity();
    }

    public void processSLIS(String slis) {
        List<String> listOfLines = lineCollector.collect(slis);

        List<Operation> allOperations = new LinkedList<>();

        listOfLines.forEach(line -> {
            try {
                allOperations.add(operationsParser.parse(line));
            } catch (CorruptedDataException e) {
                log.info("Data corrupted in line < {} >", line);
                log.info("CorruptedDataException", e);
            }
        });

        allOperations.forEach(operation -> {
            operationDAO.create(operation, 0);
        });
    }

    public void showDataFromDatabase() {
        operationDAO.list().forEach(
                operation -> {
                    System.out.println(operation);
                }
        );
    }

}
