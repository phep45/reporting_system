package com.luxoft.wordscounter;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.MutablePair;
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
    private ReportPrinter reportPrinterMock;


    private static final String PATH = "src/test/resources/simple.txt";
    private static final File FILE = new File(PATH);
    private static final List<String> EXPECTED_READER_OUTPUT = Arrays.asList("tes$t", "pest");
    private static final List<String> EXPECTED_COLLECTOR_OUTPUT = Arrays.asList("test", "pest");
    private static final Map<TreeSet<MutablePair<String, Integer>>, Integer> EXPECTED_UNIQUE_LETTERS = generateExpectedUniqueLetters();

    @SuppressWarnings("unchecked")
    @Test
    public void shouldInvokeAllMethods() throws IOException {
        onPoint.process(anyString());

        verify(textFileReaderMock).readFromFile(any(File.class));
        verify(wordsCollectorMock).collect(anyListOf(String.class));
        verify(uniqueLettersMock).count(anyListOf(String.class));
        verify(reportPrinterMock).printReport(anyMap());
    }

    @Test
    public void shouldProcessCorrectly() throws IOException{
        when(textFileReaderMock.readFromFile(FILE)).thenReturn(EXPECTED_READER_OUTPUT);
        when(wordsCollectorMock.collect(EXPECTED_READER_OUTPUT)).thenReturn(EXPECTED_COLLECTOR_OUTPUT);
        when(uniqueLettersMock.count(EXPECTED_COLLECTOR_OUTPUT)).thenReturn(EXPECTED_UNIQUE_LETTERS);

        onPoint.process(PATH);

        verify(textFileReaderMock).readFromFile(FILE);
        verify(wordsCollectorMock).collect(EXPECTED_READER_OUTPUT);
        verify(uniqueLettersMock).count(EXPECTED_COLLECTOR_OUTPUT);
        verify(reportPrinterMock).printReport(EXPECTED_UNIQUE_LETTERS);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldProcessCorrectlyBasedOnSizes() throws IOException {

        final int expectedSize = 999;

        List listOfExpectedSize = new ArrayList<>(expectedSize);
        Map uniques = new HashMap<>(expectedSize);

        for(int i = 0; i < expectedSize; i++) {
            listOfExpectedSize.add(new Object());
            uniques.put(new Object(), new Object());
        }


        when(textFileReaderMock.readFromFile(any(File.class))).thenReturn(listOfExpectedSize);
        when(wordsCollectorMock.collect(listOfExpectedSize)).thenReturn(listOfExpectedSize);
        when(uniqueLettersMock.count(listOfExpectedSize)).thenReturn(uniques);

        onPoint.process(anyString());

        verify(textFileReaderMock).readFromFile(any(File.class));
        verify(wordsCollectorMock).collect(argThat(AllOf.allOf(Matchers.hasSize(expectedSize))));
        verify(uniqueLettersMock).count(argThat(AllOf.allOf(Matchers.hasSize(expectedSize))));
        verify(reportPrinterMock).printReport(argThat(AllOf.allOf(Matchers.aMapWithSize(expectedSize))));
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
