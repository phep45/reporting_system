package com.luxoft.wordscounter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =WordsConfig.class, loader = AnnotationConfigContextLoader.class)
public class UniqueLettersTest {

    private static final List<String> listOfWords = Arrays.asList(
            "test", "user", "tes", "test", "test", "usre", "eurs", "teeeeeess", "sers", "pawel", "pawwlle", "pwwww");
    private Map<SetOfPairs, Integer> expectedMap;

    @Autowired
    private UniqueLetters uniqueLetters;

    @Test
    public void shouldReturnCorrectMap() {
        expectedMap = generateExpectedMap();

        Map<SetOfPairs, Integer> result = uniqueLetters.count(listOfWords);
        assertEquals(expectedMap, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        uniqueLetters.count(null);
    }

    private Map<SetOfPairs, Integer> generateExpectedMap() {
//        1 => [p:1, w:4]
//        1 => [e:1, r:1, s:2]
//        1 => [e:1, s:1, t:1]
//        3 => [e:1, s:1, t:2]
//        1 => [s:2, e:6, t:1]
//        3 => [e:1, r:1, s:1, u:1]
//        1 => [a:1, e:1, l:2, p:1, w:2]
//        1 => [a:1, e:1, l:1, p:1, w:1]

        Map<SetOfPairs, Integer> map = new TreeMap<>();

        SetOfPairs setOfPairs = new SetOfPairs();
        setOfPairs.insert("p");
        for (int i = 0; i < 4; i++)
            setOfPairs.insert("w");
        map.put(setOfPairs, 1);

        setOfPairs = new SetOfPairs();
        setOfPairs.insert("e");
        setOfPairs.insert("r");
        for (int i = 0; i < 2; i++)
            setOfPairs.insert("s");
        map.put(setOfPairs, 1);

        setOfPairs = new SetOfPairs();
        setOfPairs.insert("e");
        setOfPairs.insert("t");
        setOfPairs.insert("s");
        map.put(setOfPairs, 1);

        setOfPairs = new SetOfPairs();
        setOfPairs.insert("e");
        setOfPairs.insert("s");
        for (int i = 0; i < 2; i++)
            setOfPairs.insert("t");
        map.put(setOfPairs, 3);

        setOfPairs = new SetOfPairs();
        for (int i = 0; i < 6; i++)
            setOfPairs.insert("e");
        setOfPairs.insert("t");
        for (int i = 0; i < 2; i++)
            setOfPairs.insert("s");
        map.put(setOfPairs, 1);

        setOfPairs = new SetOfPairs();
        setOfPairs.insert("u");
        setOfPairs.insert("s");
        setOfPairs.insert("e");
        setOfPairs.insert("r");
        map.put(setOfPairs, 3);

        setOfPairs = new SetOfPairs();
        setOfPairs.insert("p");
        setOfPairs.insert("a");
        setOfPairs.insert("w");
        setOfPairs.insert("e");
        setOfPairs.insert("l");
        map.put(setOfPairs, 1);

        setOfPairs = new SetOfPairs();
        setOfPairs.insert("p");
        setOfPairs.insert("a");
        for (int i = 0; i < 2; i++)
            setOfPairs.insert("w");
        setOfPairs.insert("e");
        for (int i = 0; i < 2; i++)
            setOfPairs.insert("l");
        map.put(setOfPairs, 1);

        return map;
    }
}
