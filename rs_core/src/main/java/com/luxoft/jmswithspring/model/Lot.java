package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@TableName("Lot")
@XmlRootElement(name = "lot")
public class Lot {


    private int id;
    private BigDecimal price;
    private int amount;
    private String date;
    private int securityId;
    private String description;


    private Lot() {
    }

    public class Builder {

        private Builder() {
        }

        public Builder withLotId(int lotId) {
            Lot.this.id = lotId;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            Lot.this.price = price;
            return this;
        }

        public Builder withAmount(int amount) {
            Lot.this.amount = amount;
            return this;
        }

        public Builder withDate(String date) {
            Lot.this.date = date;
            return this;
        }

        public Builder withSecurityId(int productId) {
            Lot.this.securityId = productId;
            return this;
        }

        public Builder withDescription(String description) {
            Lot.this.description = description;
            return this;
        }

        public Lot build() {
            return Lot.this;
        }

    }

    public static Builder builder() {
        return new Lot().new Builder();
    }

    @XmlElement(name = "lot_id")
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @XmlElement
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @XmlElement
    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "sec_id")
    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    @XmlElement(name = "des")
    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        if (date == null || "".equals(date)) {
            return "00/00/0000";
        } else
            return date;
    }

    public int getSecurityId() {
        return securityId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("price", price)
                .add("amount", amount)
                .add("date", date)
                .add("securityId", securityId)
                .add("description", description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lot lot = (Lot) o;

        if (id != lot.id) return false;
        if (amount != lot.amount) return false;
        if (securityId != lot.securityId) return false;
        if (price != null ? !price.equals(lot.price) : lot.price != null) return false;
        if (date != null ? !date.equals(lot.date) : lot.date != null) return false;
        return description != null ? description.equals(lot.description) : lot.description == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + securityId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
