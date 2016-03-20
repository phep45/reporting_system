package com.luxoft.reportingsystem.words;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WordsCollector {

    private static final String SPECIAL_CHARACTERS = "[\\W\\D]";
    private static final String EMPTY = "";

    public List<String> collect(List<String> listOfWords) {
        Preconditions.checkNotNull(listOfWords);
        listOfWords.forEach(str -> str.replaceAll(SPECIAL_CHARACTERS, EMPTY));
        return listOfWords;
    }

}
