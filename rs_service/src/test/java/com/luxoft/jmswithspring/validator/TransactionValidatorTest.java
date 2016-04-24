package com.luxoft.jmswithspring.validator;

import com.luxoft.jmswithspring.model.Lot;
import com.luxoft.jmswithspring.model.Lots;
import com.luxoft.jmswithspring.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ValidatorTestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class TransactionValidatorTest {

    @Autowired
    private Validator<Transaction> transactionValidator;

    @Test
    public void shouldAllow() {
        Transaction allowedTransaction = allowedTransaction();
        assertTrue(transactionValidator.validate(allowedTransaction));
    }

    @Test
    public void shouldNotAllow() {
        Transaction forbiddenTransaction = forbiddenTransaction();
        assertFalse(transactionValidator.validate(forbiddenTransaction));

    }

    private Transaction allowedTransaction() {
        return Transaction.builder()
                .withBranchId(1)
                .withLots(new Lots()
                        .addLot(Lot.builder()
                            .withSecurityId(100)
                        .build())
                        .addLot(Lot.builder()
                                .withSecurityId(200)
                                .build())
                        .addLot(Lot.builder()
                                .withSecurityId(300)
                                .build())
                        .addLot(Lot.builder()
                                .withSecurityId(400)
                                .build()))
                .build();
    }

    private Transaction forbiddenTransaction() {
        return Transaction.builder()
                .withBranchId(2)
                .withLots(new Lots()
                        .addLot(Lot.builder()
                                .withSecurityId(500)
                                .build())
                        .addLot(Lot.builder()
                                .withSecurityId(600)
                                .build())
                        .addLot(Lot.builder()
                                .withSecurityId(700)
                                .build())
                        .addLot(Lot.builder()
                                .withSecurityId(800)
                                .build()))
                .build();
    }
}
