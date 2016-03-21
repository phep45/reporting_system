package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UniqueLetters {

    private static final Logger log = LoggerFactory.getLogger(UniqueLetters.class);

    private Map<TreeSet<MutablePair<String, Integer>>, Integer> map;

    public Map<TreeSet<MutablePair<String, Integer>>, Integer> count(List<String> wordsList) {
        Preconditions.checkNotNull(wordsList);
        map = new TreeMap<>(this::compareSets);

        Map tmp = wordsList.stream().map(word -> {
            return word.split(StringUtils.EMPTY);
        }).map(letters -> {
            TreeSet<MutablePair<String, Integer>> set = new TreeSet<>();
            for (String letter : letters)
                insert(letter, set);
            return set;
        }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        map = tmp;

        return map;
    }

    private void insert(String letter, TreeSet<MutablePair<String, Integer>> set) {
        for (MutablePair<String, Integer> oldPair : set) {
            if (oldPair.getLeft().equals(letter)) {
                log.trace("Pair {} exists; incrementing amount", oldPair);
                Integer right = oldPair.getRight();
                right++;
                oldPair.setRight(right);
                return;
            }
        }
        log.trace("Creating new pair: {}:1", letter);
        set.add(new MutablePair<>(letter, 1));
    }

    private void populateMap2(Map<TreeSet<MutablePair<String, Integer>>, Integer> map, TreeSet<MutablePair<String, Integer>> set) {
        if (map.containsKey(set)) {
            Integer val = map.get(set);
            val++;
            log.trace("Incremented to {} insertion of set: {}", val, set);
            map.put(set, val);
        } else {
            log.trace("First insertion of Set: {}", set);
            map.put(set, 1);
        }

    }

    private void populateMap(TreeSet<MutablePair<String, Integer>> set) {
        if (map.containsKey(set)) {
            Integer val = map.get(set);
            val++;
            log.trace("Incremented to {} insertion of set: {}", val, set);
            map.put(set, val);
        } else {
            log.trace("First insertion of Set: {}", set);
            map.put(set, 1);
        }

    }

    private int compareSets(SortedSet<MutablePair<String, Integer>> firstSet, SortedSet<MutablePair<String, Integer>> secondSet) {
        if (firstSet.equals(secondSet))
            return 0;
        if (firstSet.size() > secondSet.size())
            return 1;
        else if (firstSet.size() < secondSet.size())
            return -1;
        else {
            return compareSameSizeSets(firstSet, secondSet);
        }
    }

    private int compareSameSizeSets(SortedSet<MutablePair<String, Integer>> firstSet, SortedSet<MutablePair<String, Integer>> secondSet) {
        List<MutablePair<String, Integer>> thisSetAsList = new ArrayList<>(firstSet);
        List<MutablePair<String, Integer>> otherSetAsList = new ArrayList<>(secondSet);

        Collections.sort(thisSetAsList);
        Collections.sort(otherSetAsList);

        for (int i = 0; i < thisSetAsList.size(); i++) {
            if (thisSetAsList.get(i).compareTo(otherSetAsList.get(i)) != 0)
                return thisSetAsList.get(i).compareTo(otherSetAsList.get(i));
        }
        return 0;
    }

}
