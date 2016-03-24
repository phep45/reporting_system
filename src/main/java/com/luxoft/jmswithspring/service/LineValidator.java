package com.luxoft.jmswithspring.service;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LineValidator {

    private int validLineLength;

    @Autowired
    public LineValidator(@Value("${validator.linelength}") String validLineLength) {
        this.validLineLength = Integer.parseInt(validLineLength);
    }

    public boolean validate(String line) throws CorruptedDataException {
        if(line.length() == validLineLength)
            return true;
        else
            throw new CorruptedDataException("Line should be " + validLineLength + " characters long.");
    }

}
