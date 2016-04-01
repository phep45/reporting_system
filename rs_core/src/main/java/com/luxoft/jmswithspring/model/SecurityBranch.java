package com.luxoft.jmswithspring.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SecurityBranch {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String date;
    @XmlAttribute
    private AccessType accessType;
    @XmlElement
    private SecuritiesIds securitiesIds = new SecuritiesIds();

    public static Builder builder() {
        return new SecurityBranch().new Builder();
    }

    public class Builder {
        public Builder withId(int id) {
            SecurityBranch.this.id = id;
            return this;
        }

        public Builder withDate(String date) {
            SecurityBranch.this.date = date;
            return this;
        }

        public Builder withAccessType(AccessType accessType) {
            SecurityBranch.this.accessType = accessType;
            return this;
        }

        public Builder withSecuritiesIds(SecuritiesIds securitiesIds) {
            SecurityBranch.this.securitiesIds = securitiesIds;
            return this;
        }

        public Builder withAnotherSecurityId(int secId) {
            SecurityBranch.this.securitiesIds.add(secId);
            return this;
        }

        public SecurityBranch build() {
            return SecurityBranch.this;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public SecuritiesIds getSecuritiesIds() {
        return securitiesIds;
    }

    public void setSecuritiesIds(SecuritiesIds securitiesIds) {
        this.securitiesIds = securitiesIds;
    }
}
