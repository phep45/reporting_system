package com.luxoft.jmswithspring.manager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateConverterTest {

    private DateConverter dateConverter;
    private static final String DATE_IN_US_FORMAT = "09-23-2000";
    private static final String DATE_IN_ISO_FORMAT = "23/09/2000";

    private static final String DATE_NOT_IN_US_FORMAT = "2000/09/01";

    @Before
    public void setUp() {
        dateConverter = new DateConverter();
    }

    @Test
    public void shouldConvertCorrectly() {
        String result = dateConverter.usToIso(DATE_IN_US_FORMAT);
        assertEquals(DATE_IN_ISO_FORMAT, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowDateInNonUSFormat() {
        dateConverter.usToIso(DATE_NOT_IN_US_FORMAT);
    }

}
