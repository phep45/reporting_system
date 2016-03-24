package com.luxoft.jmswithspring.service;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.model.Transaction;

import java.math.BigDecimal;

public class TransactionMapper {

    private static final int ID_BEGIN = 0;
    private static final int ID_END = 10;
    private static final int OPERATION_BEGIN = 10;
    private static final int OPERATION_END = 16;
    private static final int CODE_BEGIN = 16;
    private static final int CODE_END = 18;
    private static final int BRANCH_ID_BEGIN = 18;
    private static final int VALID_LENGTH = 27;

    public Transaction map(String transactionAsString) {
        Preconditions.checkArgument(transactionAsString.length() == VALID_LENGTH, "Invalid input. String should be "+VALID_LENGTH+" characters long.");

        BigDecimal id = new BigDecimal(transactionAsString.substring(ID_BEGIN, ID_END).trim());
        String operation = transactionAsString.substring(OPERATION_BEGIN, OPERATION_END).trim();
        String code = transactionAsString.substring(CODE_BEGIN, CODE_END).trim();
        BigDecimal branchId = new BigDecimal(transactionAsString.substring(BRANCH_ID_BEGIN));

        return new Transaction(id, OperationType.valueOf(operation), code, branchId);
    }

}
