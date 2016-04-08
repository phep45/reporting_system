package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "security")
public class Security {

    private int branchId;
    private String date;
    private AccessType accessType;
    private List<Integer> securityIds;

    public static Builder builder() {
        return new Security().new Builder();
    }

    public class Builder {
        public Builder withId(int id) {
            Security.this.branchId = id;
            return this;
        }

        public Builder withDate(String date) {
            Security.this.date = date;
            return this;
        }

        public Builder withAccessType(AccessType accessType) {
            Security.this.accessType = accessType;
            return this;
        }

        public Builder withSecurityId(int securitiyIds) {
            Security.this.securityIds.add(securitiyIds);
            return this;
        }

        public Security build() {
            return Security.this;
        }
    }

    public int getBranchId() {
        return branchId;
    }

    @XmlAttribute(name = "branch_id")
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getDate() {
        return date;
    }

    @XmlAttribute
    public void setDate(String date) {
        this.date = date;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(AccessTypeHandler.class)
    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public List<Integer> getSecurityIds() {
        return securityIds;
    }

    @XmlElement(name = "sec_id")
    public void setSecurityIds(List<Integer> securityIds) {
        this.securityIds = securityIds;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("branchId", branchId)
                .add("date", date)
                .add("accessType", accessType)
                .add("securityIds", securityIds)
                .toString();
    }
}
