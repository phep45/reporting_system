package com.luxoft.jmswithspring.model;

import java.math.BigDecimal;
import java.util.Calendar;

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
        return "Security{" +
                "lotId=" + lotId +
                ", price=" + price +
                ", amount=" + amount +
                ", date=" + date +
                ", productId=" + productId +
                '}';
    }

}
