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
            "test", "teest");
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

        TreeSet<MutablePair<String, Integer>> setTest = new TreeSet<>();
        setTest.add(new MutablePair<>("t", 2));
        setTest.add(new MutablePair<>("e", 1));
        setTest.add(new MutablePair<>("s", 1));

        TreeSet<MutablePair<String, Integer>> setTeest = new TreeSet<>();
        setTeest.add(new MutablePair<>("t", 2));
        setTeest.add(new MutablePair<>("e", 2));
        setTeest.add(new MutablePair<>("s", 1));

        return ImmutableMap.<TreeSet<MutablePair<String, Integer>>, Integer>builder().put(setTest, 1).put(setTeest, 1).build();

    }

}
