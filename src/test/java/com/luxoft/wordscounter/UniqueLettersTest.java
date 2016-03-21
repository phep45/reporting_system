package com.luxoft.wordscounter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
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

    private ImmutableMap<SetOfPairs, Integer> generateExpectedMap() {
//        1 => [p:1, w:4]
//        1 => [e:1, r:1, s:2]
//        1 => [e:1, s:1, t:1]
//        3 => [e:1, s:1, t:2]
//        1 => [s:2, e:6, t:1]
//        3 => [e:1, r:1, s:1, u:1]
//        1 => [a:1, e:1, l:2, p:1, w:2]
//        1 => [a:1, e:1, l:1, p:1, w:1]

        SetOfPairs setOfPairs = new SetOfPairs();
        setOfPairs.insert("p");
        for (int i = 0; i < 4; i++)
            setOfPairs.insert("w");

        SetOfPairs setOfPairs2 = new SetOfPairs();
        setOfPairs2.insert("e");
        setOfPairs2.insert("r");
        for (int i = 0; i < 2; i++)
            setOfPairs2.insert("s");

        SetOfPairs setOfPairs3 = new SetOfPairs();
        setOfPairs3.insert("e");
        setOfPairs3.insert("t");
        setOfPairs3.insert("s");

        SetOfPairs setOfPairs4 = new SetOfPairs();
        setOfPairs4.insert("e");
        setOfPairs4.insert("s");
        for (int i = 0; i < 2; i++)
            setOfPairs4.insert("t");

        SetOfPairs setOfPairs5 = new SetOfPairs();
        for (int i = 0; i < 6; i++)
            setOfPairs5.insert("e");
        setOfPairs5.insert("t");
        for (int i = 0; i < 2; i++)
            setOfPairs5.insert("s");

        SetOfPairs setOfPairs6 = new SetOfPairs();
        setOfPairs6.insert("u");
        setOfPairs6.insert("s");
        setOfPairs6.insert("e");
        setOfPairs6.insert("r");

        SetOfPairs setOfPairs7 = new SetOfPairs();
        setOfPairs7.insert("p");
        setOfPairs7.insert("a");
        setOfPairs7.insert("w");
        setOfPairs7.insert("e");
        setOfPairs7.insert("l");

        SetOfPairs setOfPairs8 = new SetOfPairs();
        setOfPairs8.insert("p");
        setOfPairs8.insert("a");
        for (int i = 0; i < 2; i++)
            setOfPairs8.insert("w");
        setOfPairs8.insert("e");
        for (int i = 0; i < 2; i++)
            setOfPairs8.insert("l");

        return new ImmutableSortedMap.Builder<SetOfPairs, Integer>(SetOfPairs::compareTo)
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
}
