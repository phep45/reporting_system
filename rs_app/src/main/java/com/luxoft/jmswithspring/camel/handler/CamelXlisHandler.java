package com.luxoft.jmswithspring.camel.handler;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Transaction;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("xlisHandler")
public class CamelXlisHandler extends CamelHandler {

    @Override
    public void handle(Exchange msg) {
        String messageStr = (String) msg.getIn().getBody();

        try {
            msg.getOut().setBody(processor.processTransactionXlis(messageStr), Transaction.class);
        } catch (CorruptedDataException e) {
            log.error("Corrupted data.",e);
            msg.getOut().setBody(null, Transaction.class);
        }
    }
}
