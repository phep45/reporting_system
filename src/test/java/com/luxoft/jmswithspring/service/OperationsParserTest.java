package com.luxoft.jmswithspring.service;


import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OperationsParserTest {

    @InjectMocks
    private OperationsParser operationsParser;

    @Mock
    private Extractor extractorMock;
    @Mock
    private UserMapper userMapperMock;
    @Mock
    private TransactionMapper transactionMapperMock;
    @Mock
    private SecurityMapper securityMapperMock;

    private static final String INPUT = "000000000200001     Stiven Meckalov   BUYUS0009020020000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500";

    private Operation expectedOperation;
    private List<Security> expectedSecurities = Arrays.asList(
            new Security(130, BigDecimal.valueOf(1233.002).setScale(5), 2, "02/12/2015", 122),
            new Security(140, BigDecimal.valueOf(1033.002).setScale(5), 15, "02/12/2015", 9500)
    );

    private static final String USER_AS_STRING = "00001     Stiven Meckalov";
    private static final String SECURITIES_AS_STRING = "0000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500";
    private static final String TRANSACTION_AS_STRING = "0000000002   BUYUS000902002";

    private static final int USER_ID = 1;
    private static final String USER_NAME = "Stiven Meckalov";

    private static final int BRANCH_ID = 902002;
    private static final String COUNTRY_CODE = "US";
    private static final OperationType OPERATION_TYPE = OperationType.BUY;
    private static final int ID = 2;
    private User expectedUser;
    private Transaction expectedTransaction;

    @Test
    public void shouldParseCorrectly() throws CorruptedDataException {
        createExpectedOperation();

        when(extractorMock.extractUser(INPUT)).thenReturn(USER_AS_STRING);
        when(extractorMock.extractSecurities(INPUT)).thenReturn(SECURITIES_AS_STRING);
        when(extractorMock.extractTransaction(INPUT)).thenReturn(TRANSACTION_AS_STRING);

        when(userMapperMock.map(USER_AS_STRING)).thenReturn(expectedUser);
        when(transactionMapperMock.map(TRANSACTION_AS_STRING)).thenReturn(expectedTransaction);
        when(securityMapperMock.map(SECURITIES_AS_STRING)).thenReturn(expectedSecurities);

        Operation result = operationsParser.parse(INPUT);

        verify(extractorMock).extractUser(INPUT);
        verify(extractorMock).extractSecurities(INPUT);
        verify(extractorMock).extractTransaction(INPUT);

        verify(userMapperMock).map(USER_AS_STRING);
        verify(transactionMapperMock).map(TRANSACTION_AS_STRING);
        verify(securityMapperMock).map(SECURITIES_AS_STRING);

        assertEquals(expectedOperation, result);
    }

    private void createExpectedOperation() {
        expectedUser = new User();
        expectedUser.setUserName(USER_NAME);
        expectedUser.setUserId(USER_ID);

        expectedTransaction = new Transaction();
        expectedTransaction.setId(ID);
        expectedTransaction.setOperationType(OPERATION_TYPE);
        expectedTransaction.setCountryCode(COUNTRY_CODE);
        expectedTransaction.setBranchId(BRANCH_ID);

        expectedOperation  =  new Operation();
        expectedOperation.setSecurities(expectedSecurities);
        expectedOperation.setTransaction(expectedTransaction);
        expectedOperation.setUser(expectedUser);

    }
}


