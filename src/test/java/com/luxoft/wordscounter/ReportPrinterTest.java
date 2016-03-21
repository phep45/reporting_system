package com.luxoft.wordscounter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
