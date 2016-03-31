package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.model.User;
import com.luxoft.jmswithspring.model.Lots;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransactionConverterTest {

    private static final String XML =
            "<tran>\n" +
            "    <tran_id>120</tran_id>\n" +
            "    <user>\n" +
            "        <user_id>12</user_id>\n" +
            "        <f_name>Oleksii</f_name>\n" +
            "        <s_name>Fri</s_name>\n" +
            "        <br_date>23-05-2001</br_date>\n" +
            "    </user>\n" +
            "    <type>BUY</type>\n" +
            "    <country_code>100</country_code>\n" +
            "    <branch>\n" +
            "        <id>123</id>\n" +
            "        <address>Ckotland str. test 54</address>\n" +
            "    </branch>\n" +
            "    <lots>\n" +
            "        <lot>\n" +
            "            <date>23-12-1999</date>\n" +
            "            <lot_id>123</lot_id>\n" +
            "            <price>500.21</price>\n" +
            "            <amount>5</amount>\n" +
            "            <sec_id>655</sec_id>\n" +
            "        </lot>\n" +
            "        <lot>\n" +
            "            <date>08-03-2099</date><!--//date ISO-->\n" +
            "            <lot_id>878</lot_id>\n" +
            "            <price>34.2105</price>\n" +
            "            <amount>700</amount>\n" +
            "            <sec_id>340</sec_id>\n" +
            "        </lot>\n" +
            "    </lots>\n" +
            "</tran>";

    private static final Lots LOTS = new Lots().addLot(Security.builder().withDate("23-12-1999").withLotId(123).withPrice(BigDecimal.valueOf(500.21)).withAmount(5).withProductId(655).build())
            .addLot(Security.builder().withDate("08-03-2099").withLotId(878).withPrice(BigDecimal.valueOf(34.2105)).withAmount(700).withProductId(340).build());

    private static final Transaction expectedTransaction = Transaction.builder()
            .withId(120)
            .withUser(User.builder().withUserId(12).withFirstName("Oleksii").withSurname("Fri").withBirthDate("23-05-2001").build())
            .withOperationType(OperationType.BUY)
            .withCountryCode(100)
            .withBranchId(123)
            .withBranchAddress("Ckotland str. test 54")
            .withLots(LOTS)
            .build();

    @Test
    public void shouldParseCorrectly() {
        TransactionXmlConverter transactionXmlConverter = new TransactionXmlConverter();

        Transaction result = transactionXmlConverter.unmarshal(XML);

        assertEquals(expectedTransaction, result);
    }
}
