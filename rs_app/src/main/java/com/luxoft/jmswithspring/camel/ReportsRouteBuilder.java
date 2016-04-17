package com.luxoft.jmswithspring.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ReportsRouteBuilder extends RouteBuilder {

    private static final String ACTIVEMQ_QUEUE_SLIS_XLIS = "activemq:queue:%s";
    private static final String XLIS = "XLIS";
    private static final String SLIS = "SLIS";

    private static final List<String> mqs = Arrays.asList(SLIS, XLIS);

    @Override
    public void configure() throws Exception {
        mqs.forEach(val -> {
            from(String.format(ACTIVEMQ_QUEUE_SLIS_XLIS, val))
                    .id(val)
                    .to("log:com.luxoft.cameltest.route.MyRouteBuilder?level=DEBUG")
                    .choice()
                        .when(v -> SLIS.equals(v.getFromRouteId())).bean(SlisHandler.class)
                        .when(v -> XLIS.equals(v.getFromRouteId())).bean(XlisHandler.class)
                    .end()
                    .to("activemq:queue:TEST");
        });
    }
}
