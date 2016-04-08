package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "lots")
@XmlAccessorType(XmlAccessType.FIELD)
public class Lots {

    @XmlElement(name = "lot")
    private List<Lot> lots = new LinkedList<>();

    public List<Lot> getListOfLots() {
        return lots;
    }

    public void setListOfLots(List<Lot> listOfLots) {
        this.lots = listOfLots;
    }

    public Lots addLot(Lot lot) {
        lots.add(lot);
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lots", lots)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lots)) return false;

        Lots lots1 = (Lots) o;

        return lots != null ? lots.equals(lots1.lots) : lots1.lots == null;

    }

    @Override
    public int hashCode() {
        return lots != null ? lots.hashCode() : 0;
    }
}
