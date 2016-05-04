package com.luxoft.jmswithspring.camel;

import com.luxoft.jmswithspring.camel.handler.CamelSecForBranchHandler;
import com.luxoft.jmswithspring.camel.handler.CamelSlisHandler;
import com.luxoft.jmswithspring.camel.handler.CamelXlisHandler;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.luxoft.jmswithspring.camel.XlisDistinguisher.HEADER_TITLE;
import static com.luxoft.jmswithspring.camel.XlisDistinguisher.SECURITIES_FOR_BRANCH;
import static com.luxoft.jmswithspring.camel.XlisDistinguisher.TRANSACTION;

@Component
public class ReportsRouteBuilder extends RouteBuilder {


    private static final String ACTIVEMQ_QUEUE_SLIS_XLIS = "mqComponent:queue:%s";
    private static final String XLIS = "XLIS";
    private static final String SLIS = "SLIS";

    private static final List<String> mqs = Arrays.asList(SLIS, XLIS);

    @Autowired
    private DatabaseAccessor databaseAccessor;
    @Autowired
    private CamelSlisHandler camelSlisHandler;
    @Autowired
    private CamelXlisHandler camelXlisHandler;
    @Autowired
    private CamelSecForBranchHandler camelSecForBranchHandler;
    @Autowired
    private XlisDistinguisher xlisDistinguisher;

    @Override
    public void configure() throws Exception {
//        mqs.forEach(val -> {
//            from(String.format(ACTIVEMQ_QUEUE_SLIS_XLIS, val))
//                    .id(val)
//                    .to("log:com.luxoft.cameltest.route.MyRouteBuilder?level=INFO")
////                    .to("direct:slisHandler")
//                    .choice()
//                        .when(SLIS::equals).bean(camelSlisHandler, "handle").bean(databaseAccessor, "saveTransaction")
//                        .when(XLIS::equals).bean(xlisDistinguisher)
//                            .choice()
//                                .when(header(HEADER_TITLE).isEqualTo(TRANSACTION)).bean(databaseAccessor, "saveTransaction")
//                                .when(header(HEADER_TITLE).isEqualTo(SECURITIES_FOR_BRANCH)).bean(databaseAccessor, "saveSecuritiesForBranch")
//                            .end()
//                    .end();
//        });


        from("mqComponent:queue:SLIS").to("direct:slisHandler");
        from("mqComponent:queue:XLIS").to("direct:xlisDistinguisher");


        from("direct:slisHandler")
                .bean(camelSlisHandler)
                .bean(databaseAccessor, "saveTransaction");

        from("direct:xlisDistinguisher")
                .bean(xlisDistinguisher)
                .choice()
                    .when(header(HEADER_TITLE).isEqualTo(TRANSACTION)).to("direct:xlisHandler")
                    .when(header(HEADER_TITLE).isEqualTo(SECURITIES_FOR_BRANCH)).to("direct:secForBranchHandler")
                .end();

        from("direct:xlisHandler")
                .bean(camelXlisHandler)
                .bean(databaseAccessor, "saveTransaction");

        from("direct:secForBranchHandler")
                .bean(camelSecForBranchHandler)
                .bean(databaseAccessor, "saveSecuritiesForBranch");

    }

    //transformation
    //validate route
    //save route
    //enrichment
    //output

}
