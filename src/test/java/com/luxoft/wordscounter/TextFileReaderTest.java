package com.luxoft.wordscounter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class TextFileReaderTest {

    private TextFileReader textFileReader;
    private File file;

    private static final String testFileName = "src/test/resources/test.txt";
    private static final List<String> expectedOutputForFileTest = Arrays.asList(
            "te#st", "user", "tes", "test", "test", "u@sre", "eurs", "teeeeeess", "se+rs", "pawel", "pawwlle,", "pwwww");

    private static final String invalidFilename = "src/test/resources/test.java";

    @Before
    public void setUp() {
        textFileReader = new TextFileReader();
    }

    @Test
    public void shouldReadFile() throws IOException {
        assertNotNull("textFileReader is null.", textFileReader);

        file = new File(testFileName);
        List<String> result = textFileReader.readFromFile(file);
        assertEquals(expectedOutputForFileTest, result);
    }

    @Test
    public void shouldReadFileWithMocks() throws IOException {
        file = new File(testFileName);

        textFileReader = Mockito.mock(TextFileReader.class);
        when(textFileReader.readFromFile(file)).thenReturn(expectedOutputForFileTest);

    }

    @Test
    public void shouldThrowExceptionWithMocks() throws IOException {
        file = new File(invalidFilename);

        textFileReader = Mockito.mock(TextFileReader.class);
        when(textFileReader.readFromFile(file)).thenThrow(IOException.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() throws IOException {
        assertNotNull("textFileReader is null.", textFileReader);

        file = new File(invalidFilename);
        textFileReader.readFromFile(file);
    }

}
