package com.yl.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoListVo {
    @JsonProperty("id")
    private Integer productInfoId;

    @JsonProperty("name")
    private String productInfoName;

    @JsonProperty("price")
    private BigDecimal productInfoPrice;

    @JsonProperty("description")
    private String productInfoDescription;

    @JsonProperty("icon")
    private String productInfoIcon;

}
