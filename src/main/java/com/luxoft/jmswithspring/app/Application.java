package com.luxoft.jmswithspring.app;

import com.luxoft.jmswithspring.config.OperationsConfig;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Operation;
import com.luxoft.jmswithspring.service.OperationsParser;
import com.luxoft.jmswithspring.service.LineCollector;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperationsConfig.class);

        File file = new File("C:\\Users\\Prosner\\IdeaProjects\\reporting_system\\src\\main\\resources\\SINPUT.txt");

        OperationsParser operationsParser = (OperationsParser) context.getBean("operationsParser");
        LineCollector lineCollector = (LineCollector) context.getBean("lineCollector");

        List<String> listOfLines = new LinkedList<>();

        try {
            listOfLines = lineCollector.collect(FileUtils.readFileToString(file));

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Operation> allOperations = new LinkedList<>();

        listOfLines.forEach(line -> {
            try {
                allOperations.add(operationsParser.parse(line));
            } catch (CorruptedDataException e) {
                log.info("Data corrupted in line < {} >", line);
            }
        });

        allOperations.forEach(System.out::println);

        context.close();
    }

}
