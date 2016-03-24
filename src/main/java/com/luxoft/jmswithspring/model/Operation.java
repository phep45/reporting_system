package com.luxoft.jmswithspring.model;

import java.util.List;

public class Operation {

    private User user;
    private Transaction transaction;
    private List<Security> securities;

    public Operation(){}

    public Operation(User user, Transaction transaction, List<Security> securities) {
        this.user = user;
        this.transaction = transaction;
        this.securities = securities;
    }

    public List<Security> getSecurities() {
        return securities;
    }

    public void setSecurities(List<Security> securities) {
        this.securities = securities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
