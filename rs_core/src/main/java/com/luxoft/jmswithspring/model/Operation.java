package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user", user)
                .add("transaction", transaction)
                .add("securities", securities)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;

        Operation operation = (Operation) o;

        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (transaction != null ? !transaction.equals(operation.transaction) : operation.transaction != null)
            return false;
        return securities != null ? securities.equals(operation.securities) : operation.securities == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (transaction != null ? transaction.hashCode() : 0);
        result = 31 * result + (securities != null ? securities.hashCode() : 0);
        return result;
    }
}
