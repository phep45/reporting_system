package com.luxoft.jmswithspring.app;

import com.luxoft.jmswithspring.config.OperationsConfig;
import com.luxoft.jmswithspring.database.dao.GenericDAO;
import com.luxoft.jmswithspring.database.dao.TablesDAO;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Operation;
import com.luxoft.jmswithspring.service.LineCollector;
import com.luxoft.jmswithspring.service.OperationsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TablesDAO tablesDAO;
    @Autowired
    private OperationsParser operationsParser;
    @Autowired
    private LineCollector lineCollector;
    @Autowired
    private GenericDAO<Operation> operationDAO;


    public void run() {
        tablesDAO.createTableUser();
        tablesDAO.createTableTransaction();
        tablesDAO.createTableSecurity();

        List<String> listOfLines = lineCollector.collect("000000000200001     Stiven Meckalov   BUYUS0009020020000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500\n" +
                "000000000400021            Lu Cheng  SELLGB0001122220000000170000000887.01200000402/13/2015002560000000180000001033.00200001502/13/2015094400000000190000001033.00200001502/13/201509500\n" +
                "000000000500001     Stiven MeckalovMOVE_OUS0001122220000005050000000020.00000000202/13/201500123\n" +
                "000000000800301     Adam Nowak     CANCELEU0002224420000005050000000330.00005400206/23/201600901");

//        List<String> listOfLines = new LinkedList<>();
//        listOfLines.add(queue.poll());

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

    public static void main(String[] args) throws IOException, CorruptedDataException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperationsConfig.class);

        Application app = (Application) context.getBean("application");
        app.run();

        context.close();
    }

}
