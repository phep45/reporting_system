package com.luxoft.jmswithspring.model;

import java.math.BigDecimal;

public class Transaction {

    private BigDecimal id;
    private OperationType operationType;
    private String countryCode;
    private BigDecimal branchId;

    public Transaction() {
        this(BigDecimal.ZERO,OperationType.DUMMY,"",BigDecimal.ZERO);
    }

    public Transaction(BigDecimal id, OperationType operationType, String countryCode, BigDecimal branchId) {
        this.id = id;
        this.operationType = operationType;
        this.countryCode = countryCode;
        this.branchId = branchId;
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

    public BigDecimal getBranchId() {
        return branchId;
    }

    public void setBranchId(BigDecimal branchId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (operationType != null ? !operationType.equals(that.operationType) : that.operationType != null)
            return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        return branchId != null ? branchId.equals(that.branchId) : that.branchId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (branchId != null ? branchId.hashCode() : 0);
        return result;
    }
}
