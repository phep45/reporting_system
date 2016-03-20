package com.luxoft.wordscounter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =Config.class, loader = AnnotationConfigContextLoader.class)
public class ReportPrinterTest {

    @Autowired
    ReportPrinter reportPrinter;

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        reportPrinter = new ReportPrinter();
        reportPrinter.printReport(null);
    }

}
