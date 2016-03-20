package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UniqueLetters {

    private static final String EMPTY_STR = "";

    private Map<EntrySet, Integer> mapped;

    public Map<EntrySet, Integer> count(List<String> wordsList) {
        Preconditions.checkNotNull(wordsList);
        mapped = new TreeMap<>();
        wordsList.forEach(s -> {
            List<String> letters = Arrays.asList(s.split(EMPTY_STR));
            EntrySet set = new EntrySet();
            letters.forEach(set::incrementIfExists
            );
            populateMap(set);
        });
        return mapped;
    }
    private void populateMap(EntrySet set) {
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
