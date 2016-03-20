package com.luxoft.wordscounter;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TextFileReaderTest {

    private TextFileReader textFileReader;
    private File file;

    private String testFileName = "src/test/resources/test.txt";
    private List<String> expectedOutputForFileTest = Arrays.asList(
            "te#st", "user", "tes", "test", "test", "u@sre", "eurs", "teeeeeess", "se+rs", "pawel", "pawwlle,", "pwwww");

    private String invalidFilename = "src/test/resources/test.java";

    @Before
    public void setUp() {
        textFileReader = new TextFileReader();
    }

    @Test
    public void shouldReadFile() throws IOException {
        file = new File(testFileName);
        List<String> result = textFileReader.readFromFile(file);
        assertEquals(expectedOutputForFileTest, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() throws IOException {
        file = new File(invalidFilename);
        textFileReader.readFromFile(file);
    }

}
