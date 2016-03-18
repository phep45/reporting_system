package com.luxoft.reportingsystem.words;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsCollector {

    public static final String SPECIAL_CHARACTERS = "[\\W\\D]";
    private static final String TEXT_FILE_EXTENSION = ".txt";
    public static final String SPACE = " ";
    private File file;

    public WordsCollector(File file) {
        if (!file.getName().endsWith(TEXT_FILE_EXTENSION))
            throw new IllegalArgumentException();
        this.file = file;
    }

    public List<String> collect() {
        List<String> wordsList = new LinkedList<>();

        try {
            final String[] split = FileUtils.readFileToString(file).split(SPACE);
            return Arrays.asList(split).stream()
                    .map(val -> val.replaceAll(SPECIAL_CHARACTERS, SPACE))
                    .collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordsList;
    }

    public static void main(String[] args) {
        WordsCollector wordsCollector = new WordsCollector(new File("src\\main\\resources\\words\\test2.txt"));
        System.out.println(wordsCollector.collect().size());
    }

}
