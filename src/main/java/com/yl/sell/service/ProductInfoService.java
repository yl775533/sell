package com.yl.sell.service;

import com.yl.sell.dto.CartDto;
import com.yl.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findOne(Integer productId);

    //查询在架的商品
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //扣库存
    void decreaseStock(List<CartDto> cartDtoList);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);

    //商品上架
    ProductInfo onSale(Integer productId);

    //商品下架
    ProductInfo offSale(Integer productId);
}
