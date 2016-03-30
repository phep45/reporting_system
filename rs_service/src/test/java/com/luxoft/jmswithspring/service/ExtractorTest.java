package com.luxoft.jmswithspring.service;

import com.luxoft.jmswithspring.service.Extractor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExtractorTest {

    private Extractor extractor = new Extractor();

    private static final String INPUT = "000000000200001     Stiven Meckalov   BUYUS0009020020000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500";
    private static final String EXPECTED_USER_STR = "00001     Stiven Meckalov";
    private static final String EXPECTED_TRANSACTION = "0000000002   BUYUS000902002";
    private static final String EXPECTED_SECURITIES = "0000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500";

    @Test
    public void shouldExtractUser() {
        String result = extractor.extractUser(INPUT);
        assertEquals(EXPECTED_USER_STR, result);
    }

    @Test
    public void shouldExtractTransaction() {
        String result = extractor.extractTransaction(INPUT);
        assertEquals(EXPECTED_TRANSACTION, result);
    }

    @Test
    public void shouldExtractSecurities() {
        String result = extractor.extractSecurities(INPUT);
        assertEquals(EXPECTED_SECURITIES, result);
    }
}
