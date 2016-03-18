package com.luxoft.reportingsystem.words;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class WordsCollector {

    public static final String SPECIAL_CHARACTERS = "[\\W\\D]";
    private static final String TEXT_FILE_EXTENSION = ".txt";
    public static final String EMPTY_STR = "";
    private File file;

    public WordsCollector(File file) {
        if(!file.getName().endsWith(TEXT_FILE_EXTENSION))
            throw new IllegalArgumentException();
        this.file = file;
    }

    public List<String> collect() {
        List<String> wordsList = new LinkedList<>();

        try {
            String[] fileStr = FileUtils.readFileToString(file).split(EMPTY_STR);
            for(String str : fileStr) {
                str.replaceAll(SPECIAL_CHARACTERS, EMPTY_STR);
                wordsList.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordsList;
    }

}
