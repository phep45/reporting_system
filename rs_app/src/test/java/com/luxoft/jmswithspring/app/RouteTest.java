package com.luxoft.jmswithspring.app;


import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.cdi.CamelCdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CamelCdiRunner.class)
public class RouteTest {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:activemq:slis")
    protected ProducerTemplate template;

    @Before
    public void setUp() {
        resultEndpoint.reset();
    }

    @Test
    public void testSendMessage() throws Exception {
        String expectedBody = "";

        template.sendBodyAndHeader(expectedBody, "foo", "bar");

        resultEndpoint.assertIsSatisfied();
    }

    static class ContextConfig extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("direct:activemq:slis").filter(header("foo").isEqualTo("bar")).to("mock:result");
        }
    }

}
