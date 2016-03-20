package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UniqueLetters {

    public static final String EMPTY_STR = "";

    public Map<Set<EntryPair>, Integer> countUniques(List<String> wordsList) {
        Preconditions.checkNotNull(wordsList);
        Map<Set<EntryPair>, Integer> map = new HashMap<>();
        wordsList.forEach(s -> {
            List<String> letters = Arrays.asList(s.split(EMPTY_STR));
            Set<EntryPair> set = new TreeSet<>();
            letters.forEach(letter ->
                    incrementIfExists(set, letter)
            );
            populateMap(map, set);
        });
        return map;
    }

    private void incrementIfExists(Set<EntryPair> set, String letter) {
        for (EntryPair oldPair : set) {
            if (oldPair.getLetter().equals(letter)) {
                oldPair.incrementAmount();
                return;
            }
        }
        set.add(new EntryPair(letter, 1));
    }

    private void populateMap(Map<Set<EntryPair>, Integer> map, Set<EntryPair> set) {
        if (!map.containsKey(set))
            map.put(set, 1);
        else {
            Integer val = map.get(set);
            val++;
            map.put(set, val);
        }
    }

}
