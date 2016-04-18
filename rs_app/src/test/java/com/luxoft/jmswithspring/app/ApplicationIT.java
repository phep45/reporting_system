package com.luxoft.jmswithspring.app;

import com.jayway.awaitility.Awaitility;
import com.luxoft.jmswithspring.camel.CamelConfig;
import com.luxoft.jmswithspring.camel.CamelSlisHandler;
import com.luxoft.jmswithspring.camel.CamelXlisHandler;
import com.luxoft.jmswithspring.camel.ReportsRouteBuilder;
import com.luxoft.jmswithspring.config.OperationsConfig;
import com.luxoft.jmswithspring.database.dao.TransactionDAO;
import com.luxoft.jmswithspring.model.*;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OperationsConfig.class, CamelConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class ApplicationIT extends CamelTestSupport {

    public static final String EMBEDDED = "vm://localhost?broker.persistent=false";
    private static final String ACTIVE_MQ = "tcp://localhost:61616";

    private static final String SLIS_MSG = "000000000800301     Adam Nowak     CANCELEU0002224420000005050000000330.00005400206/23/201600901";
    public static final int TRANSACTION_ID = 8;

    @Autowired
    private ReportsRouteBuilder reportsRouteBuilder;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionDAO transactionDAO;

    protected String startEndpointUri = "testmq:queue:SLIS";

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldHandleSLIS() {

        sendExchange(SLIS_MSG);
        Awaitility.await().until(newTransactionAdded());

        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "TRANSACTION"));

        assertEquals(expectedSLISTransaction(), transactionDAO.get(TRANSACTION_ID));

    }

    private Callable<Boolean> newTransactionAdded() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return JdbcTestUtils.countRowsInTable(jdbcTemplate, "TRANSACTION") == 1;
            }
        };
    }

    private void sendExchange(final Object expectedBody) {
        template.sendBodyAndHeader(startEndpointUri, expectedBody, "test", 123);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        context.addComponent("testmq", activeMQComponent(ACTIVE_MQ));

        return context;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            private final String ACTIVEMQ_QUEUE_SLIS_XLIS = "testmq:queue:%s";
            private final String XLIS = "XLIS";
            private final String SLIS = "SLIS";
            private final List<String> mqs = Arrays.asList(SLIS, XLIS);
            @Override
            public void configure() throws Exception {
                mqs.forEach(val -> {
                    from(String.format(ACTIVEMQ_QUEUE_SLIS_XLIS, val))
                            .id(val)
                            .choice()
                            .when(v -> SLIS.equals(v.getFromRouteId())).bean(CamelSlisHandler.class)
                            .when(v -> XLIS.equals(v.getFromRouteId())).bean(CamelXlisHandler.class)
                            .end()
                            .to("testmq:queue:TEST");
                });
            }
        };
    }

    private Transaction expectedSLISTransaction() {
        return Transaction.builder()
                .withId(8)
                .withUser(User.builder().withUserId(301).withFirstName("Adam").withSurname("Nowak").withBirthDate("00/00/0000").build())
                .withCountryCode("EU")
                .withOperationType(OperationType.CANCEL)
                .withLots(new Lots().addLot(Lot.builder().withSecurityId(901).withLotId(5050).withDate("06/23/2016").withPrice(BigDecimal.valueOf(330.00005)).withAmount(4002).build()))
                .withBranchId(222442)
                .build();
    }

}
