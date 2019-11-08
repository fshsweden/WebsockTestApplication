package com.fsh.demo.models;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class Order {
    @NonNull public String productId;
    @NonNull public Integer qty;
    @NonNull public BigDecimal price;

    public Order(String productId, Integer qty, Double fltprice) {
        this.productId = productId;
        this.qty = qty;
        this.price = BigDecimal.valueOf(fltprice);
    }
}
