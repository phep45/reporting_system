package com.luxoft.wordscounter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =Config.class, loader = AnnotationConfigContextLoader.class)
public class TextFileReaderTest {

    @Autowired
    private TextFileReader textFileReader;
    private File file;

    private String testFileName = "src/test/resources/test.txt";
    private List<String> expectedOutputForFileTest = Arrays.asList(
            "te#st", "user", "tes", "test", "test", "u@sre", "eurs", "teeeeeess", "se+rs", "pawel", "pawwlle,", "pwwww");

    private String invalidFilename = "src/test/resources/test.java";

    @Test
    public void shouldReadFile() throws IOException {
        assertNotNull("textFileReader is null.", textFileReader);

        file = new File(testFileName);
        List<String> result = textFileReader.readFromFile(file);
        assertEquals(expectedOutputForFileTest, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() throws IOException {
        assertNotNull("textFileReader is null.", textFileReader);

        file = new File(invalidFilename);
        textFileReader.readFromFile(file);
    }

}
