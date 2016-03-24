package com.luxoft.jmswithspring.app;

import com.luxoft.jmswithspring.config.OperationsConfig;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import com.luxoft.jmswithspring.service.OperationsParser;
import com.luxoft.jmswithspring.service.TextCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.List;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperationsConfig.class);

        File file = new File("C:\\Users\\Prosner\\IdeaProjects\\reporting_system\\src\\main\\resources\\SINPUT.txt");

        OperationsParser operationsParser = (OperationsParser) context.getBean("operationsParser");
        TextCollector textCollector = (TextCollector) context.getBean("textCollector");

//        List<String> collected = textCollector.collect(file);
//        operationsParser.setList(collected);
//
//        List<User> users = operationsParser.parseUsers();
//        List<Transaction> transactions = operationsParser.parseTransactions();
//        List<Security> securities = operationsParser.parseSecurities();

        context.close();
    }

}
