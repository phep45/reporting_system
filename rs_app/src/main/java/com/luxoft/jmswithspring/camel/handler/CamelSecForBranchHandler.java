package com.luxoft.jmswithspring.camel.handler;

import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import org.springframework.stereotype.Component;

@Component
public class CamelSecForBranchHandler extends CamelHandler<SecuritiesForBranches> {
    @Override
    public SecuritiesForBranches handle(String msg) {
        return processor.processSecForBranchesXlis(msg);
    }
}
