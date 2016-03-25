package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

public class Security {

    private int lotId;
    private BigDecimal price;
    private int amount;
    private String date;
    private int productId;

    public Security() {
        this(0, BigDecimal.ZERO, 0, "00/00/00", 0);
    }

    public Security(int lotId, BigDecimal price, int amount, String date, int productId) {
        this.lotId = lotId;
        this.price = price;
        this.amount = amount;
        this.date = date;
        this.productId = productId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
