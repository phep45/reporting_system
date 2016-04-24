package com.luxoft.jmswithspring.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ReportsRouteBuilder extends RouteBuilder {



    private static final String ACTIVEMQ_QUEUE_SLIS_XLIS = "mqComponent:queue:%s";
    private static final String XLIS = "XLIS";
    private static final String SLIS = "SLIS";

    private static final List<String> mqs = Arrays.asList(SLIS, XLIS);

    @Autowired
    DatabaseAccessor databaseAccessor;

    @Override
    public void configure() throws Exception {
        mqs.forEach(val -> {
            from(String.format(ACTIVEMQ_QUEUE_SLIS_XLIS, val))
                    .id(val)
                    .to("log:com.luxoft.cameltest.route.MyRouteBuilder?level=INFO")
                    .choice()
                        .when(SLIS::equals).to("direct:slisHandler")
                        .when(XLIS::equals).to("direct:xlisDistinguisher")
                    .end();

//                    .to("direct:transformation")
//                    .to("data:")
//                    .to("server")
//                    .to("mqComponent:queue:TEST");
        });

        from("direct:slisHandler").to("bean:save?method=saveTransaction");

        from("direct:xlisDistinguisher")
                .choice()
                    .when(body().isEqualTo(true)).to("bean:save?method=saveTransaction")
                    .when(body().isEqualTo(false)).to("bean:save?method=saveSecForBranch")
                .end();

//        from("bean:save?method=saveTransaction").to("mqComponent:queue:OUT");

//        from("transformation")
//                .bean()
    }

    //transformation
    //validate route
    //save route
    //enrichment
    //output

}
