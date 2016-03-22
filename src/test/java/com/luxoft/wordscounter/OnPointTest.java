package com.luxoft.wordscounter;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class OnPointTest {


    private TextFileReader textFileReaderMock;
    private WordsCollector wordsCollectorMock;
    private UniqueLetters uniqueLettersMock;

    private static final File file = new File("src/test/resources/simple.txt");
    private static final List<String> expectedReaderOutput = Arrays.asList("tes$t", "pest");
    private static final List<String> expectedCollectorOutput = Arrays.asList("test", "pest");

    private static final Map<TreeSet<MutablePair<String, Integer>>, Integer> expectedUniqueLetters = generateMap();

    @Before
    public void setUp() {
        textFileReaderMock = Mockito.mock(TextFileReader.class);
        wordsCollectorMock = Mockito.mock(WordsCollector.class);
        uniqueLettersMock = Mockito.mock(UniqueLetters.class);
    }

    @Test
    public void shouldPass() throws IOException{

        when(textFileReaderMock.readFromFile(file)).thenReturn(expectedReaderOutput);
        when(wordsCollectorMock.collect(expectedReaderOutput)).thenReturn(expectedCollectorOutput);
        when(uniqueLettersMock.count(expectedCollectorOutput)).thenReturn(expectedUniqueLetters);

        assertEquals(textFileReaderMock.readFromFile(file), new TextFileReader().readFromFile(file));
        assertEquals(wordsCollectorMock.collect(expectedReaderOutput), new WordsCollector().collect(expectedReaderOutput));
        assertEquals(uniqueLettersMock.count(expectedCollectorOutput), new UniqueLetters().count(expectedCollectorOutput));

    }

    private static Map<TreeSet<MutablePair<String, Integer>>, Integer> generateMap() {
        Map<TreeSet<MutablePair<String, Integer>>, Integer> map = new HashMap<>();

        TreeSet<MutablePair<String, Integer>> set1 = new TreeSet<>();
        set1.add(new MutablePair<>("t", 2));
        set1.add(new MutablePair<>("e", 1));
        set1.add(new MutablePair<>("s", 1));

        TreeSet<MutablePair<String, Integer>> set2 = new TreeSet<>();
        set2.add(new MutablePair<>("t", 1));
        set2.add(new MutablePair<>("e", 1));
        set2.add(new MutablePair<>("s", 1));
        set2.add(new MutablePair<>("p", 1));

        return ImmutableMap.<TreeSet<MutablePair<String, Integer>>, Integer>builder().put(set1, 1).put(set2 ,1).build();
    }

}
