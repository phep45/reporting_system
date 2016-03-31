package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.Transaction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class TransactionXmlConverter extends XmlConverter<Transaction> {
    @Override
    public Transaction unmarshal(String xml) {
        Transaction transaction = null;

        try {
            jaxbContext = JAXBContext.newInstance(Transaction.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            transaction = (Transaction) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        } catch (JAXBException e) {
            log.info("",e);
            return null;
        }

        return transaction;
    }

    public static void main(String[] args) {
        TransactionXmlConverter transactionConverter = new TransactionXmlConverter();
        String xml = "<tran>\n" +
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

        Transaction transaction = transactionConverter.unmarshal(xml);

        System.out.println(transaction);
    }
}
