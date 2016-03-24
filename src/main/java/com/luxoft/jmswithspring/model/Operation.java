package com.luxoft.jmswithspring.model;

import java.util.List;

public class Operation {

    private User user;
    private Transaction transaction;
    private List<Security> securities;

    public Operation(User user, Transaction transaction, List<Security> securities) {
        this.user = user;
        this.transaction = transaction;
        this.securities = securities;
    }

}
