package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.*;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransactionConverterTest {

    private static final File file = new File("src/test/resources/transaction.xml");
    private String transaction;


    @Before
    public void setUp() throws IOException {
        transaction = FileUtils.readFileToString(file);
    }

    @Test
    public void shouldParseCorrectly() throws CorruptedDataException {
        TransactionXmlConverter transactionXmlConverter = new TransactionXmlConverter();

        Transaction result = transactionXmlConverter.unmarshal(transaction);

        assertEquals(expectedTransaction, result);
    }

    private static final Lots LOTS = new Lots()
            .addLot(Lot.builder().withDate("03-12-1999").withLotId(123).withPrice(BigDecimal.valueOf(500.21)).withAmount(5).withSecurityId(655).withDescription("PEPSI LTD").build())
            .addLot(Lot.builder().withDate("08-03-2099").withLotId(878).withPrice(BigDecimal.valueOf(34.2105)).withAmount(700).withSecurityId(340).withDescription("MCSF LTD").build());

    private static final Transaction expectedTransaction = Transaction.builder()
            .withId(120)
            .withUser(User.builder().withUserId(12).withFirstName("Oleksii").withSurname("Fri").withBirthDate("03-05-2001").build())
            .withOperationType(OperationType.BUY)
            .withCountryCode(100)
            .withBranchId(123)
            .withBranchAddress("Scotland str. test 54")
            .withLots(LOTS)
            .build();
}
