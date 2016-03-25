package com.luxoft.jmswithspring.service;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private static final Logger log = LoggerFactory.getLogger(TransactionMapper.class);

    private static final int ID_BEGIN = 0;
    private static final int ID_END = 10;
    private static final int OPERATION_BEGIN = 10;
    private static final int OPERATION_END = 16;
    private static final int CODE_BEGIN = 16;
    private static final int CODE_END = 18;
    private static final int BRANCH_ID_BEGIN = 18;
    private static final int VALID_LENGTH = 27;

    public Transaction map(String transactionAsString) throws CorruptedDataException {
        Preconditions.checkArgument(transactionAsString.length() == VALID_LENGTH, "Invalid input. String should be " + VALID_LENGTH + " characters long.");

        Transaction transaction = null;

        try {
            int id = Integer.parseInt(transactionAsString.substring(ID_BEGIN, ID_END).trim());
            String operation = transactionAsString.substring(OPERATION_BEGIN, OPERATION_END).trim();
            String code = transactionAsString.substring(CODE_BEGIN, CODE_END).trim();
            int branchId = Integer.parseInt(transactionAsString.substring(BRANCH_ID_BEGIN));

            transaction = new Transaction(id, OperationType.valueOf(operation), code, branchId);

        } catch (IllegalArgumentException e) {
            log.info("Data < {} > corrupted.", transactionAsString);
            throw new CorruptedDataException(e);
        }

        return transaction;
    }

}
