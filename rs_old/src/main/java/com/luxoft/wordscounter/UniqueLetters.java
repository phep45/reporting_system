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

    public Map<TreeSet<MutablePair<String, Integer>>, Integer> count(List<String> wordsList) {
        Preconditions.checkNotNull(wordsList);

        return wordsList.stream()
                .map(word -> word.split(StringUtils.EMPTY))
                .map(letters -> {
                    TreeSet<MutablePair<String, Integer>> set = new TreeSet<>();
                    for (String letter : letters)
                        insert(letter, set);
                    return set;
                })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().intValue()
                ));

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

}
