package com.luxoft.wordscounter;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.MutablePair;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
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
    private static final File file = new File(path);
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

    @SuppressWarnings("unchecked")
    @Test
    public void shouldProcessCorrectlyBaseOnSizes() throws IOException {

        final int expectedSize = expectedReaderOutput.size();

        when(textFileReaderMock.readFromFile(file)).thenReturn(expectedReaderOutput);
        when(wordsCollectorMock.collect(expectedReaderOutput)).thenReturn(expectedCollectorOutput);
        when(uniqueLettersMock.count(expectedCollectorOutput)).thenReturn(expectedUniqueLetters);

        onPoint.process(path);

        verify(textFileReaderMock).readFromFile(file);
        verify(wordsCollectorMock).collect(argThat(AllOf.allOf(Matchers.hasSize(expectedSize))));
        verify(uniqueLettersMock).count(argThat(AllOf.allOf(Matchers.hasSize(expectedSize))));
        verify(reportPrinter).printReport(argThat(new BaseMatcher<Map<TreeSet<MutablePair<String, Integer>>, Integer>>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matches(Object o) {
                return ((Map<TreeSet<MutablePair<String, Integer>>, Integer>) o).size() == 2;
            }
        }));
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
