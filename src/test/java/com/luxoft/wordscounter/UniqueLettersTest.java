package com.luxoft.wordscounter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class UniqueLettersTest {

    private static final List<String> listOfWords = Arrays.asList(
            "test", "user", "tes", "test", "test", "usre", "eurs", "teeeeeess", "sers", "pawel", "pawwlle", "pwwww");
    private Map expectedMap;

    private UniqueLetters uniqueLetters;

    @Before
    public void setUp() {
        uniqueLetters = new UniqueLetters();
    }

    @Test
    public void shouldReturnCorrectMap() {
        expectedMap = generateExpectedMap();

        Map<TreeSet<MutablePair<String, Integer>>, Integer> result = uniqueLetters.count(listOfWords);
        assertEquals(expectedMap, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        uniqueLetters.count(null);
    }

    private ImmutableMap<TreeSet<MutablePair<String, Integer>>, Integer> generateExpectedMap() {
//        1 => [p:1, w:4]
//        1 => [e:1, r:1, s:2]
//        1 => [e:1, s:1, t:1]
//        3 => [e:1, s:1, t:2]
//        1 => [s:2, e:6, t:1]
//        3 => [e:1, r:1, s:1, u:1]
//        1 => [a:1, e:1, l:2, p:1, w:2]
//        1 => [a:1, e:1, l:1, p:1, w:1]

        TreeSet<MutablePair<String, Integer>> setOfPairs = new TreeSet<>();
        setOfPairs.add(new MutablePair<>("p", 1));
        setOfPairs.add(new MutablePair<>("w", 4));

        TreeSet<MutablePair<String, Integer>> setOfPairs2 = new TreeSet<>();
        setOfPairs2.add(new MutablePair<>("e", 1));
        setOfPairs2.add(new MutablePair<>("r", 1));
        setOfPairs2.add(new MutablePair<>("s", 2));

        TreeSet<MutablePair<String, Integer>> setOfPairs3 = new TreeSet<>();
        setOfPairs3.add(new MutablePair<>("e", 1));
        setOfPairs3.add(new MutablePair<>("s", 1));
        setOfPairs3.add(new MutablePair<>("t", 1));

        TreeSet<MutablePair<String, Integer>> setOfPairs4 = new TreeSet<>();
        setOfPairs4.add(new MutablePair<>("e", 1));
        setOfPairs4.add(new MutablePair<>("s", 1));
        setOfPairs4.add(new MutablePair<>("t", 2));

        TreeSet<MutablePair<String, Integer>> setOfPairs5 = new TreeSet<>();
        setOfPairs5.add(new MutablePair<>("e", 6));
        setOfPairs5.add(new MutablePair<>("t", 1));
        setOfPairs5.add(new MutablePair<>("s", 2));

        TreeSet<MutablePair<String, Integer>> setOfPairs6 = new TreeSet<>();
        setOfPairs6.add(new MutablePair<>("u", 1));
        setOfPairs6.add(new MutablePair<>("s", 1));
        setOfPairs6.add(new MutablePair<>("e", 1));
        setOfPairs6.add(new MutablePair<>("r", 1));

        TreeSet<MutablePair<String, Integer>> setOfPairs7 = new TreeSet<>();
        setOfPairs7.add(new MutablePair<>("p", 1));
        setOfPairs7.add(new MutablePair<>("a", 1));
        setOfPairs7.add(new MutablePair<>("w", 1));
        setOfPairs7.add(new MutablePair<>("e", 1));
        setOfPairs7.add(new MutablePair<>("l", 1));

        TreeSet<MutablePair<String, Integer>> setOfPairs8 = new TreeSet<>();
        setOfPairs8.add(new MutablePair<>("p", 1));
        setOfPairs8.add(new MutablePair<>("a", 1));
        setOfPairs8.add(new MutablePair<>("w", 2));
        setOfPairs8.add(new MutablePair<>("e", 1));
        setOfPairs8.add(new MutablePair<>("l", 2));

        return new ImmutableSortedMap.Builder<TreeSet<MutablePair<String, Integer>>, Integer>(this::compareSets)
                .put(setOfPairs, 1)
                .put(setOfPairs2, 1)
                .put(setOfPairs3, 1)
                .put(setOfPairs4, 3)
                .put(setOfPairs5, 1)
                .put(setOfPairs6, 3)
                .put(setOfPairs7, 1)
                .put(setOfPairs8, 1)
                .build();
    }

    private int compareSets(SortedSet<MutablePair<String, Integer>> firstSet, SortedSet<MutablePair<String, Integer>> secondSet) {
        if(firstSet.equals(secondSet))
            return 0;
        if(firstSet.size() > secondSet.size())
            return 1;
        else if(firstSet.size() < secondSet.size())
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

        for(int i = 0; i < thisSetAsList.size(); i++) {
            if(thisSetAsList.get(i).compareTo(otherSetAsList.get(i)) != 0)
                return thisSetAsList.get(i).compareTo(otherSetAsList.get(i));
        }
        return 0;
    }
}
