package com.luxoft.jmswithspring.app;

import com.luxoft.jmswithspring.config.OperationsConfig;
import com.luxoft.jmswithspring.database.generic.GenericDAO;
import com.luxoft.jmswithspring.database.operation.OperationJDBCTemplate;
import com.luxoft.jmswithspring.database.operation.TablesDAO;
import com.luxoft.jmswithspring.database.user.UserJDBCTemplate;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Operation;
import com.luxoft.jmswithspring.model.TableName;
import com.luxoft.jmswithspring.model.User;
import com.luxoft.jmswithspring.service.LineCollector;
import com.luxoft.jmswithspring.service.OperationsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException, CorruptedDataException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperationsConfig.class);

        TablesDAO tablesDAO = (TablesDAO) context.getBean("tablesDAO");
        tablesDAO.createTableUser();
        tablesDAO.createTableTransaction();
        tablesDAO.createTableSecurity();

        GenericDAO<User> userDAO = (UserJDBCTemplate) context.getBean("userJDBCTemplate");


        userDAO.create(new User(12,"eee"), 12);
        userDAO.create(new User(13, "ppp"), 13);

        GenericDAO<User> dao = (UserJDBCTemplate) context.getBean("userJDBCTemplate");

        dao.delete(12, User.class);




        File file = new File("C:\\Users\\Prosner\\IdeaProjects\\reporting_system\\src\\main\\resources\\SINPUT.txt");

        OperationsParser operationsParser = (OperationsParser) context.getBean("operationsParser");
        LineCollector lineCollector = (LineCollector) context.getBean("lineCollector");

        List<String> listOfLines = new LinkedList<>();


            listOfLines = lineCollector.collect("000000000200001     Stiven Meckalov   BUYUS0009020020000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500");

        List<Operation> allOperations = new LinkedList<>();


        listOfLines.forEach(line -> {
            try {
                allOperations.add(operationsParser.parse(line));
            } catch (CorruptedDataException e) {
                log.info("Data corrupted in line < {} >", line);
                e.printStackTrace();
            }
        });

        GenericDAO<Operation> operationDAO = (OperationJDBCTemplate) context.getBean("operationJDBCTemplate");

        allOperations.forEach(operation -> {

                operationDAO.create(operation, 0);

        });

        context.close();
    }

}
