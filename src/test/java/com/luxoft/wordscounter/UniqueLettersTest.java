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
import java.util.function.Function;
import java.util.stream.Collectors;

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
        List<TreeSet<MutablePair<String, Integer>>> listOfSets = new LinkedList<>();

        TreeSet<MutablePair<String, Integer>> setOfPairs = new TreeSet<>();
        setOfPairs.add(new MutablePair<>("p", 1));
        setOfPairs.add(new MutablePair<>("w", 4));
        listOfSets.add(setOfPairs);

        TreeSet<MutablePair<String, Integer>> setOfPairs2 = new TreeSet<>();
        setOfPairs2.add(new MutablePair<>("e", 1));
        setOfPairs2.add(new MutablePair<>("r", 1));
        setOfPairs2.add(new MutablePair<>("s", 2));
        listOfSets.add(setOfPairs2);

        TreeSet<MutablePair<String, Integer>> setOfPairs3 = new TreeSet<>();
        setOfPairs3.add(new MutablePair<>("e", 1));
        setOfPairs3.add(new MutablePair<>("s", 1));
        setOfPairs3.add(new MutablePair<>("t", 1));
        listOfSets.add(setOfPairs3);

        TreeSet<MutablePair<String, Integer>> setOfPairs4 = new TreeSet<>();
        setOfPairs4.add(new MutablePair<>("e", 1));
        setOfPairs4.add(new MutablePair<>("s", 1));
        setOfPairs4.add(new MutablePair<>("t", 2));
        for(int i = 0; i < 3; i++)
            listOfSets.add(setOfPairs4);

        TreeSet<MutablePair<String, Integer>> setOfPairs5 = new TreeSet<>();
        setOfPairs5.add(new MutablePair<>("e", 6));
        setOfPairs5.add(new MutablePair<>("t", 1));
        setOfPairs5.add(new MutablePair<>("s", 2));
        listOfSets.add(setOfPairs5);

        TreeSet<MutablePair<String, Integer>> setOfPairs6 = new TreeSet<>();
        setOfPairs6.add(new MutablePair<>("u", 1));
        setOfPairs6.add(new MutablePair<>("s", 1));
        setOfPairs6.add(new MutablePair<>("e", 1));
        setOfPairs6.add(new MutablePair<>("r", 1));
        for(int i = 0; i < 3; i++)
            listOfSets.add(setOfPairs6);

        TreeSet<MutablePair<String, Integer>> setOfPairs7 = new TreeSet<>();
        setOfPairs7.add(new MutablePair<>("p", 1));
        setOfPairs7.add(new MutablePair<>("a", 1));
        setOfPairs7.add(new MutablePair<>("w", 1));
        setOfPairs7.add(new MutablePair<>("e", 1));
        setOfPairs7.add(new MutablePair<>("l", 1));
        listOfSets.add(setOfPairs7);

        TreeSet<MutablePair<String, Integer>> setOfPairs8 = new TreeSet<>();
        setOfPairs8.add(new MutablePair<>("p", 1));
        setOfPairs8.add(new MutablePair<>("a", 1));
        setOfPairs8.add(new MutablePair<>("w", 2));
        setOfPairs8.add(new MutablePair<>("e", 1));
        setOfPairs8.add(new MutablePair<>("l", 2));
        listOfSets.add(setOfPairs8);


        return ImmutableMap.copyOf((Map) listOfSets.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

    }

}
