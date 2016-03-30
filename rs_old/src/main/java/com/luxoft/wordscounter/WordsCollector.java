package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WordsCollector {
    private static final Logger log = LoggerFactory.getLogger(WordsCollector.class);

    private static final String SPECIAL_CHARACTERS = "\\W";
    private static final String EMPTY = "";

    public List<String> collect(List<String> listOfWords) {
        Preconditions.checkNotNull(listOfWords);
        log.trace("Words to process: {}", listOfWords);
        return listOfWords.stream()
                .map(str -> str.replaceAll(SPECIAL_CHARACTERS, EMPTY))
                .collect(Collectors.toList());
    }

}
