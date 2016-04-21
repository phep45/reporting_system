package com.luxoft.jmswithspring.camel.handler;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class CamelSlisHandler extends CamelHandler<Transaction> {

    @Override
    public Transaction handle(String msg) {
        try {
            return processor.processSlis(msg);

        } catch (CorruptedDataException e) {
            log.error("Corrupted data.",e);
            return null;
        }
    }
}