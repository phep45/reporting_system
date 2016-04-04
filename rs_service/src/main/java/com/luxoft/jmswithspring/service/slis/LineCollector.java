package com.luxoft.jmswithspring.service.slis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LineCollector {
    private static final Logger log = LoggerFactory.getLogger(LineCollector.class);

    public static final String NEW_LINE = "\\n";

    public List<String> collect(String string) {
        List<String> lines;
        lines = Arrays.asList(string.split(NEW_LINE));

        log.info("Collected {} lines.", lines.size());

        return lines;
    }

}
