package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@TableName("Security")
@XmlRootElement(name = "lot")
@XmlAccessorType(XmlAccessType.FIELD)
public class Security {

    @XmlElement(name = "lot_id")
    private int lotId;
    @XmlElement
    private BigDecimal price;
    @XmlElement
    private int amount;
    @XmlElement
    private String date;
    @XmlElement(name = "sec_id")
    private int productId;

    private Security() {
    }

    public class Builder {

        private Builder() {
        }

        public Builder withLotId(int lotId) {
            Security.this.lotId = lotId;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            Security.this.price = price;
            return this;
        }

        public Builder withAmount(int amount) {
            Security.this.amount = amount;
            return this;
        }

        public Builder withDate(String date) {
            Security.this.date = date;
            return this;
        }

        public Builder withProductId(int productId) {
            Security.this.productId = productId;
            return this;
        }

        public Security build() {
            return Security.this;
        }

    }

    public static Builder builder() {
        return new Security().new Builder();
    }


    public int getLotId() {
        return lotId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public int getProductId() {
        return productId;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lotId", lotId)
                .add("price", price)
                .add("amount", amount)
                .add("date", date)
                .add("productId", productId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Security)) return false;

        Security security = (Security) o;

        if (lotId != security.lotId) return false;
        if (amount != security.amount) return false;
        if (productId != security.productId) return false;
        if (price != null ? !price.equals(security.price) : security.price != null) return false;
        return date != null ? date.equals(security.date) : security.date == null;

    }

    @Override
    public int hashCode() {
        int result = lotId;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + productId;
        return result;
    }
}
