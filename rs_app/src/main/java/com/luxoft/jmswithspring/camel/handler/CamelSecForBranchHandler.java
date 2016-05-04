package com.luxoft.jmswithspring.camel.handler;

import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class CamelSecForBranchHandler extends CamelHandler {
    @Override
    public void handle(Exchange msg) {
        String messageStr = (String) msg.getIn().getBody();
        msg.getOut().setBody(processor.processSecForBranchesXlis(messageStr), SecuritiesForBranches.class);
    }
}
