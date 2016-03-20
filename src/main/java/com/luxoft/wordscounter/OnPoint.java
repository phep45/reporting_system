package com.luxoft.wordscounter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class OnPoint {

    private static final Logger log = LoggerFactory.getLogger(OnPoint.class);

    @Autowired
    private TextFileReader reader;
    @Autowired
    private WordsCollector wordsCollector;
    @Autowired
    private UniqueLetters uniqueLetters;
    @Autowired
    private ReportPrinter printer;

    public void process(String path) {

        File file = new File(path);
        try {
            List<String> words = reader.readFromFile(file);
            log.trace("Words read from file");
            List<String> collectedWords = wordsCollector.collect(words);
            log.trace("Words collected");
            Map<SetOfPairs, Integer> result = uniqueLetters.count(collectedWords);
            log.trace("Unique letters counted");
            printer.printReport(result);
            log.trace("Report printed");

        } catch (IOException e) {
            log.error("Could not read from file: " + file.getName(), e);
        }

    }

    public static void main(String... args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WordsConfig.class);

        OnPoint onPoint = context.getBean(OnPoint.class);
        onPoint.process("src\\main\\resources\\words\\test2.txt");

        context.close();
    }

    public WordsCollector getWordsCollector() {
        return wordsCollector;
    }

    public void setWordsCollector(WordsCollector wordsCollector) {
        this.wordsCollector = wordsCollector;
    }

    public TextFileReader getReader() {
        return reader;
    }

    public void setReader(TextFileReader reader) {
        this.reader = reader;
    }

    public UniqueLetters getUniqueLetters() {
        return uniqueLetters;
    }

    public void setUniqueLetters(UniqueLetters uniqueLetters) {
        this.uniqueLetters = uniqueLetters;
    }

    public ReportPrinter getPrinter() {
        return printer;
    }

    public void setPrinter(ReportPrinter printer) {
        this.printer = printer;
    }
}


