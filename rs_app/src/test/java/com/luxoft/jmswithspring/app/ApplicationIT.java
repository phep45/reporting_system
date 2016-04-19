package com.luxoft.jmswithspring.app;

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
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;
import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OperationsConfig.class, CamelConfig.class},
        loader = AnnotationConfigContextLoader.class)
public class ApplicationIT extends CamelTestSupport {

    public static final String EMBEDDED = "vm://localhost?broker.persistent=false";
    private static final String ACTIVE_MQ = "tcp://localhost:61616";

    private static final String SLIS_MSG = "000000000800301     Adam Nowak     CANCELEU0002224420000005050000000330.00005400206/23/201600901";
    private static final int TRANSACTION_ID = 8;

    private static final int XLIS_TRANSACTION_ID = 120;
    private static String XLIS_MSG;

    static {
        try {
            XLIS_MSG = FileUtils.readFileToString(new File("src/test/resources/transaction.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final String TABLE_TRANSACTION = "TRANSACTION";

    @Autowired
    private ReportsRouteBuilder reportsRouteBuilder;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionDAO transactionDAO;

    protected String slisEndpointUri = "testmq:queue:SLIS";
    protected String xlisEndpointUri = "testmq:queue:XLIS";

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSendToSlisQueueReadAndSaveInDatabase() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_TRANSACTION);
        sendMessageToSlisQueue(SLIS_MSG);
        await().until(() -> countRowsInTable(jdbcTemplate, TABLE_TRANSACTION) == rows + INTEGER_ONE);

        assertEquals(expectedSLISTransaction(), transactionDAO.get(TRANSACTION_ID));

    }

    @Test
    public void shouldSendToXlisQueueReadAndSaveInDatabase() {
        int rows = countRowsInTable(jdbcTemplate, TABLE_TRANSACTION);

        sendMessageToXlisQueue(XLIS_MSG);
        await().until(() -> countRowsInTable(jdbcTemplate, TABLE_TRANSACTION) == rows + INTEGER_ONE);

        assertEquals(expectedXLISTransaction(), transactionDAO.get(XLIS_TRANSACTION_ID));
    }

    private void sendMessageToSlisQueue(final Object expectedBody) {
        template.sendBodyAndHeader(slisEndpointUri, expectedBody, "test", 123);
    }

    private void sendMessageToXlisQueue(final Object expectedBody) {
        template.sendBodyAndHeader(xlisEndpointUri, expectedBody, "test", 123);
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

    private Transaction expectedXLISTransaction() {
        return Transaction.builder()
                .withId(120)
                .withUser(User.builder()
                        .withUserId(12)
                        .withFirstName("Oleksii")
                        .withSurname("Fri")
                        .withBirthDate("03-05-2001")
                        .build())
                .withOperationType(OperationType.BUY)
                .withCountryCode(100)
                .withBranchId(123)
                .withBranchAddress("Scotland str. test 54")
                .withLots(new Lots()
                        .addLot(Lot.builder()
                                .withDate("12/03/1999")
                                .withLotId(123)
                                .withPrice(BigDecimal.valueOf(500.21).setScale(5, BigDecimal.ROUND_HALF_UP))
                                .withAmount(5)
                                .withSecurityId(655)
                                .build())
                        .addLot(Lot.builder()
                                .withDate("03/08/2099")
                                .withLotId(878)
                                .withPrice(BigDecimal.valueOf(34.2105).setScale(5, BigDecimal.ROUND_HALF_UP))
                                .withAmount(700)
                                .withSecurityId(340)
                                .build()))
                .build();
    }

}
