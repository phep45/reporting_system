package com.luxoft.jmswithspring.model;


import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class Transaction {

    private int id;

    private OperationType operationType;
    private String countryCode;
    private int branchId;

    public Transaction() {
        this(0,OperationType.DUMMY, "",0);
    }

    public Transaction(int id, OperationType operationType, String countryCode, int branchId) {
        this.id = id;
        this.operationType = operationType;
        this.countryCode = countryCode;
        this.branchId = branchId;
    }

    public void setId(int id) {
        this.id = id;
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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("operationType", operationType)
                .add("countryCode", countryCode)
                .add("branchId", branchId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (branchId != that.branchId) return false;
        if (operationType != that.operationType) return false;
        return countryCode != null ? countryCode.equals(that.countryCode) : that.countryCode == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + branchId;
        return result;
    }
}
