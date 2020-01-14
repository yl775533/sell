package com.yl.sell.dao;

import com.yl.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDAO extends JpaRepository<ProductInfo,Integer> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
}