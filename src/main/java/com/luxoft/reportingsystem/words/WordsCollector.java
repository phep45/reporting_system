package com.luxoft.reportingsystem.words;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WordsCollector {

    public static final String TEXT_FILE_EXTENSION = ".txt";
    private File file;

    public WordsCollector(File file) {
        if(!file.getName().endsWith(TEXT_FILE_EXTENSION))
            throw new IllegalArgumentException();
        this.file = file;
    }

    public List<String> collect() {
        List<String> wordsList = new LinkedList<>();

        try (Scanner scan = new Scanner(new FileInputStream(file))) {

            while(scan.hasNext()) {
                String word = scan.next();
                word = word.replaceAll("[\\W\\D]]","");
                wordsList.add(word);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return wordsList;
    }

}
