package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class WordsCollector {

    private static final String SPECIAL_CHARACTERS = "[\\W]";
    private static final String EMPTY = "";

    public List<String> collect(List<String> listOfWords) {
        Preconditions.checkNotNull(listOfWords);
        List<String> collected = new LinkedList<>();

        listOfWords.forEach(str -> {
            str = str.replaceAll(SPECIAL_CHARACTERS, EMPTY);
            collected.add(str);
        });

        return collected;
    }

}
