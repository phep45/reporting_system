package com.luxoft.jmswithspring.service;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TextCollector {
    private static final Logger log = LoggerFactory.getLogger(TextCollector.class);

    public static final String NEW_LINE = "\\n";

    public List<String> collect(File file) {
        Preconditions.checkArgument(file.getName().endsWith(".txt"), "Only TXT files supported");

        try {

            List<String> lines =  Arrays.asList(FileUtils.readFileToString(file).split(NEW_LINE));
            log.info("Collected {} lines.", lines.size());
            return lines;

        } catch (IOException e) {
            log.error("Could not read from file: {}", file, e);
            return new ArrayList<>();
        }
    }

}
