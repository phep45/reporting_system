package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "securities_branches")
public class SecuritiesForBranches {

    private List<Security> securities;

    public List<Security> getSecurities() {
        return securities;
    }

    @XmlElement(name = "security")
    public void setSecurities(List<Security> securities) {
        this.securities = securities;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("securities", securities)
                .toString();
    }
}
