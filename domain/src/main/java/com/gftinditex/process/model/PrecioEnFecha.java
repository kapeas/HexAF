package com.gftinditex.process.model;


import jakarta.persistence.*;

import java.sql.Timestamp;

//TODO: imports JPA.
@Entity(name = "Prices")
public class PrecioEnFecha {

    @Id
    @Column(name = "product_id")
    @GeneratedValue
    public Integer productId;


    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "start_date")
    private Timestamp start;

    @Column(name = "end_date")
    private Timestamp end;

    @Column(name = "price_list")
    private Integer price_list_order;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "price")
    private Float price;

    @Column(name = "curr")
    private String currency;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getPrice_list_order() {
        return price_list_order;
    }

    public void setPrice_list_order(Integer price_list_order) {
        this.price_list_order = price_list_order;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
