package com.luxoft.jmswithspring.model;


import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@TableName("Transaction")
@XmlRootElement(name = "tran")
public class Transaction {

    private int id;
    private User user;
    private OperationType type;

    private CountryCode countryCode;
    private Branch branch = new Branch();
    private Lots lots = new Lots();

    public Transaction() {}

    public static Builder builder() {
        return new Transaction().new Builder();
    }

    public class Builder {
        public Builder withId(int id) {
            Transaction.this.id = id;
            return this;
        }

        public Builder withOperationType(OperationType operationType) {
            Transaction.this.type = operationType;
            return this;
        }

        public Builder withCountryCode(int countryCode) {
            Transaction.this.countryCode = CountryCode.match(countryCode);
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            Transaction.this.countryCode = CountryCode.valueOf(countryCode);
            return this;
        }

        public Builder withBranchId(int branchId) {
            Transaction.this.branch.setId(branchId);
            return this;
        }

        public Builder withBranchAddress(String branchAddress) {
            Transaction.this.branch.setAddress(branchAddress);
            return this;
        }

        public Builder withBranch(Branch branch) {
            Transaction.this.branch = branch;
            return this;
        }

        public Builder withUser(User user) {
            Transaction.this.user = user;
            return this;
        }

        public Builder withLots(Lots lots) {
            Transaction.this.lots = lots;
            return this;
        }

        public Transaction build() {
            return Transaction.this;
        }
    }

    @XmlElement(name = "country_code")
    @XmlJavaTypeAdapter(CountryCodeHandler.class)
    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    @XmlElement(name = "tran_id")
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public void setUser(User user) {
        this.user = user;
    }

    @XmlElement
    public void setType(OperationType type) {
        this.type = type;
    }

    @XmlElement
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @XmlElement
    public void setLots(Lots lots) {
        this.lots = lots;
    }

    public int getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }


    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = CountryCode.valueOf(countryCode);
    }

    public int getBranchId() {
        return branch.getId();
    }

    public void setBranchId(int branchId) {
        this.branch.setId(branchId);
    }

    public String getBranchAddress() {
        return branch.getAddress();
    }

    public User getUser() {
        return user;
    }

    public Lots getLots() {
        return lots;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("user", user)
                .add("type", type)
                .add("countryCode", countryCode)
                .add("branch", branch)
                .add("lots", lots)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (type != that.type) return false;
        if (countryCode != that.countryCode) return false;
        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        return lots != null ? lots.equals(that.lots) : that.lots == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (lots != null ? lots.hashCode() : 0);
        return result;
    }
}

