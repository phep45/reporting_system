package com.luxoft.jmswithspring.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SecuritiesForBranches {

    @XmlAttribute
    private int branchId;
    @XmlAttribute
    private String date;
    @XmlAttribute
    private AccessType accessType;
    @XmlElement
    private int securityId;

    public static Builder builder() {
        return new SecuritiesForBranches().new Builder();
    }

    public class Builder {
        public Builder withId(int id) {
            SecuritiesForBranches.this.branchId = id;
            return this;
        }

        public Builder withDate(String date) {
            SecuritiesForBranches.this.date = date;
            return this;
        }

        public Builder withAccessType(AccessType accessType) {
            SecuritiesForBranches.this.accessType = accessType;
            return this;
        }

        public Builder withSecurityIds(int securitiyIds) {
            SecuritiesForBranches.this.securityId = securitiyIds;
            return this;
        }

        public SecuritiesForBranches build() {
            return SecuritiesForBranches.this;
        }
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }
}
