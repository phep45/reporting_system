package com.luxoft.jmswithspring.service.xlis;

import com.luxoft.jmswithspring.model.Transaction;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class TransactionXmlConverter extends XmlConverter<Transaction> {
    @Override
    public Transaction unmarshal(String xml) {
        Transaction transaction = null;

        try {
            jaxbContext = JAXBContext.newInstance(Transaction.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            transaction = (Transaction) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        } catch (JAXBException e) {
            log.info("Unable to unmarshal",e);
            return null;
        }

        return transaction;
    }

}
