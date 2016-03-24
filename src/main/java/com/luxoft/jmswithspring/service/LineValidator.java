package com.luxoft.jmswithspring.service;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;


public class LineValidator {

    private int validLineLength;

    public LineValidator(int validLineLength) {
        this.validLineLength = validLineLength;
    }

    public boolean validate(String line) throws CorruptedDataException {
        if(line.length() == validLineLength)
            return true;
        else
            throw new CorruptedDataException("Line should be " + validLineLength + " characters long.");
    }

}
