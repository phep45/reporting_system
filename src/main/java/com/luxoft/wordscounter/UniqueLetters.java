package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UniqueLetters {

    private static final Logger log = LoggerFactory.getLogger(UniqueLetters.class);
    private static final String EMPTY_STR = "";

    private Map<SetOfPairs, Integer> map;

    public Map<SetOfPairs, Integer> count(List<String> wordsList) {
        Preconditions.checkNotNull(wordsList);
        map = new TreeMap<>();
        wordsList.forEach(s -> {
            log.trace("Processing word: {}", s);
            List<String> letters = Arrays.asList(s.split(EMPTY_STR));
            SetOfPairs set = new SetOfPairs();
            letters.forEach(set::insert);
            populateMap(set);
        });
        return map;
    }
    private void populateMap(SetOfPairs set) {
        if (!map.containsKey(set)) {
            log.trace("First insertion of Set: {}", set);
            map.put(set, 1);
        }
        else {
            Integer val = map.get(set);
            val++;
            log.trace("Incremented to {} insertion of set: {}", val, set);
            map.put(set, val);
        }
    }

}
