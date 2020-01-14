package com.yl.sell.dto;

import lombok.Data;

@Data
public class CartDto {
    private Integer productId;
    private Integer productQuantity;

    public CartDto(Integer productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
