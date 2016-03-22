package com.luxoft.wordscounter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public class TextFileReader {

    private static final Logger log = LoggerFactory.getLogger(TextFileReader.class);

    private static final String TEXT_FILE_EXTENSION = ".txt";

    public List<String> readFromFile(File file) throws IOException {
        checkArgument(file.getName().endsWith(TEXT_FILE_EXTENSION), "Only TXT files supported");
        log.trace("File to read: {}", file.getName());
        try {
            final String[] split = FileUtils.readFileToString(file).split(StringUtils.SPACE);
            return Arrays.asList(split);
        } catch (IOException e) {
           throw new IOException(e);
        }
    }

}
