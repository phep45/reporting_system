package com.luxoft.wordscounter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =WordsConfig.class, loader = AnnotationConfigContextLoader.class)
public class WordsCollectorTest {

    @Autowired
    private WordsCollector wordsCollector;

    private List<String> expectedListOfWords = Arrays.asList(
            "test", "user", "tes", "test", "test", "usre", "eurs", "teeeeeess", "sers", "pawel", "pawwlle", "pwwww");
    private List<String> wordsToProcess = Arrays.asList(
            "te#st", "user", "tes", "test", "test", "u@sre", "eurs", "teeeeeess", "se+rs", "pawel", "pawwlle,", "pwwww");

    @Test
    public void shouldCollectWords() throws IOException {
        List<String> result = wordsCollector.collect(wordsToProcess);
        assertEquals(expectedListOfWords, result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        wordsCollector.collect(null);
    }


}
