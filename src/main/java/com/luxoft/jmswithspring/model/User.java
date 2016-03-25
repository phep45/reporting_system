package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class User {

    private int userId;
    private String userName;

    private List<Transaction> transactions = new LinkedList<>();

    public User() {
        this(0,"");
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;

    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ImmutableList<Transaction> getTransactions() {
        return ImmutableList.copyOf(transactions);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("transactions", transactions)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        return transactions != null ? transactions.equals(user.transactions) : user.transactions == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
        return result;
    }
}
