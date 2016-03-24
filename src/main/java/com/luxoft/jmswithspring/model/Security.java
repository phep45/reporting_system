package com.luxoft.jmswithspring.model;

import java.math.BigDecimal;
import java.util.Calendar;

public class Security {

    private BigDecimal lotId;
    private BigDecimal price;
    private BigDecimal amount;
    private String date;
    private BigDecimal productId;

    public Security() {
        this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, BigDecimal.ZERO);
    }

    public Security(BigDecimal lotId, BigDecimal price, BigDecimal amount, String date, BigDecimal productId) {
        this.lotId = lotId;
        this.price = price;
        this.amount = amount;
        this.date = date;
        this.productId = productId;
    }

    public BigDecimal getLotId() {
        return lotId;
    }

    public void setLotId(BigDecimal lotId) {
        this.lotId = lotId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Security{" +
                "lotId=" + lotId +
                ", price=" + price +
                ", amount=" + amount +
                ", date=" + date +
                ", productId=" + productId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Security)) return false;

        Security security = (Security) o;

        if (lotId != null ? !lotId.equals(security.lotId) : security.lotId != null) return false;
        if (price != null ? !price.equals(security.price) : security.price != null) return false;
        if (amount != null ? !amount.equals(security.amount) : security.amount != null) return false;
        if (date != null ? !date.equals(security.date) : security.date != null) return false;
        return productId != null ? productId.equals(security.productId) : security.productId == null;

    }

    @Override
    public int hashCode() {
        int result = lotId != null ? lotId.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
