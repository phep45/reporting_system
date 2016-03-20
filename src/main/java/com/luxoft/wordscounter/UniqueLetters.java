package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UniqueLetters {

    private static final String EMPTY_STR = "";

    private Map<SetOfPairs, Integer> mapped;

    public Map<SetOfPairs, Integer> count(List<String> wordsList) {
        Preconditions.checkNotNull(wordsList);
        mapped = new TreeMap<>();
        wordsList.forEach(s -> {
            List<String> letters = Arrays.asList(s.split(EMPTY_STR));
            SetOfPairs set = new SetOfPairs();
            letters.forEach(set::insert);
            populateMap(set);
        });
        return mapped;
    }
    private void populateMap(SetOfPairs set) {
        if (!mapped.containsKey(set)) {
            mapped.put(set, 1);
        }
        else {
            Integer val = mapped.get(set);
            val++;
            mapped.put(set, val);
        }
    }

}
