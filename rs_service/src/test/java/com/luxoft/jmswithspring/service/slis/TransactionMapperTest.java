package com.luxoft.jmswithspring.service.slis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.OperationType;
import com.luxoft.jmswithspring.model.Transaction;
import com.luxoft.jmswithspring.service.slis.TransactionMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionMapperTest {

    private TransactionMapper transactionMapper;
    private Transaction expectedTransaction;

    private static final String INPUT = "0000000002   BUYUS000902002";
    private static final String TOO_SHORT_INPUT = "0000000002BUYUS000902002";
    private static final String CORRUPTED_INPUT = "0000000002   GETUS000902002";

    private static final int BRANCH_ID = 902002;
    private static final String COUNTRY_CODE = "US";
    private static final OperationType OPERATION_TYPE = OperationType.BUY;
    private static final int ID = 2;

    @Before
    public void setUp() {
        transactionMapper = new TransactionMapper();
    }

    @Test
    public void shouldReturnTransaction() throws CorruptedDataException {
        expectedTransaction = Transaction.builder()
                .withBranchId(BRANCH_ID)
                .withCountryCode(COUNTRY_CODE)
                .withOperationType(OPERATION_TYPE)
                .withId(ID)
                .build();

        Transaction result = transactionMapper.map(INPUT);

        assertEquals(expectedTransaction, result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowTooShortInputs() throws CorruptedDataException {
        transactionMapper.map(TOO_SHORT_INPUT);
    }

    @Test(expected = CorruptedDataException.class)
    public void shouldThrowException() throws CorruptedDataException {
        transactionMapper.map(CORRUPTED_INPUT);
    }
}
