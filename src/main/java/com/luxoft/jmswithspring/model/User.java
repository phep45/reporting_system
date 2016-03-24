package com.luxoft.jmswithspring.model;

import com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class User {

    private BigDecimal userId;
    private String userName;

    private List<Transaction> transactions = new LinkedList<>();

    public User() {
        this(BigDecimal.ZERO,"");
    }

    public User(BigDecimal userId, String userName) {
        this.userId = userId;
        this.userName = userName;

    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ImmutableList<Transaction> getTransactions() {
        return ImmutableList.copyOf(transactions);
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }

}
