package com.luxoft.wordscounter;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowire;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OnPointTest {


    @InjectMocks
    private OnPoint onPoint;
    @Mock
    private TextFileReader textFileReaderMock;
    @Mock
    private WordsCollector wordsCollectorMock;
    @Mock
    private UniqueLetters uniqueLettersMock;
    @Mock
    private ReportPrinter reportPrinter;


    private static final String path = "src/test/resources/simple.txt";
    private static final File file = new File(path );
    private static final List<String> expectedReaderOutput = Arrays.asList("tes$t", "pest");
    private static final List<String> expectedCollectorOutput = Arrays.asList("test", "pest");
    private static final Map<TreeSet<MutablePair<String, Integer>>, Integer> expectedUniqueLetters = generateExpectedUniqueLetters();


    @Test
    public void shouldInvokeAllMethods() throws IOException {
        onPoint.process(anyString());

        verify(textFileReaderMock).readFromFile(any(File.class));
        verify(wordsCollectorMock).collect(anyListOf(String.class));
        verify(uniqueLettersMock).count(anyListOf(String.class));
        verify(reportPrinter).printReport(anyMap());
    }

    @Test
    public void shouldProcessCorrectly() throws IOException{
        when(textFileReaderMock.readFromFile(file)).thenReturn(expectedReaderOutput);
        when(wordsCollectorMock.collect(expectedReaderOutput)).thenReturn(expectedCollectorOutput);
        when(uniqueLettersMock.count(expectedCollectorOutput)).thenReturn(expectedUniqueLetters);

        onPoint.process(path);

        verify(textFileReaderMock).readFromFile(file);
        verify(wordsCollectorMock).collect(expectedReaderOutput);
        verify(uniqueLettersMock).count(expectedCollectorOutput);
        verify(reportPrinter).printReport(expectedUniqueLetters);
    }

    private static ImmutableMap<TreeSet<MutablePair<String, Integer>>, Integer> generateExpectedUniqueLetters() {
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
