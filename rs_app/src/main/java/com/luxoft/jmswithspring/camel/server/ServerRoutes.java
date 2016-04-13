package com.luxoft.jmswithspring.camel.server;

import org.apache.camel.builder.RouteBuilder;

public class ServerRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:queue:SLIS").to("slisDataHandler");
        from("jms:queue:XLIS").to("xlisDataHandler");

    }
}
