package com.luxoft.jmswithspring.model;


import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class Transaction {

    private int id;
    private OperationType operationType;
    private String countryCode;
    private int branchId;

    private List<Security> securities = new LinkedList<>();

    public Transaction() {
        this(0,OperationType.DUMMY, "",0);
    }

    public Transaction(int id, OperationType operationType, String countryCode, int branchId) {
        this.id = id;
        this.operationType = operationType;
        this.countryCode = countryCode;
        this.branchId = branchId;
    }

    public void addSecurity(Security security) {
        securities.add(security);
    }

    public ImmutableList<Security> getSecurities() {
        return ImmutableList.copyOf(securities);
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", operationType='" + operationType + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", branchId=" + branchId +
                '}';
    }

}
