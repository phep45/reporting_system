package com.luxoft.jmswithspring.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class SecuritiesIds {

    @XmlElement(name = "sec_id")
    private List<Integer> secIds;

    public List<Integer> getSecIds() {
        return secIds;
    }

    public void setSecIds(List<Integer> secIds) {
        this.secIds = secIds;
    }

    public boolean add(Integer secId) {
        return secIds.add(secId);
    }
}
