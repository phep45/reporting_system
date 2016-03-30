package com.luxoft.wordscounter;

import org.junit.Before;
import org.junit.Test;

public class ReportPrinterTest {

    private ReportPrinter reportPrinter;

    @Before
    public void setUp() {
        reportPrinter = new ReportPrinter();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        reportPrinter = new ReportPrinter();
        reportPrinter.printReport(null);
    }

}
