package com.luxoft.reportingsystem.words;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public class TextFileReader {

    private static final String TEXT_FILE_EXTENSION = ".txt";
    private static final String SPACE = " ";

    public List<String> readFromFile(File file) throws IOException {
        checkArgument(file.getName().endsWith(TEXT_FILE_EXTENSION), "Only TXT files supported");
        try {
            final String[] split = FileUtils.readFileToString(file).split(SPACE);
            return Arrays.asList(split);
        } catch (IOException e) {
           throw new IOException(e);
        }
    }

}
