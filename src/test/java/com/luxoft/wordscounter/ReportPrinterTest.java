package com.luxoft.wordscounter;

import org.junit.Test;

public class ReportPrinterTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        ReportPrinter reportPrinter = new ReportPrinter();
        reportPrinter.printReport(null);
    }

}
