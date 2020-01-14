package com.yl.sell.dao;

import com.yl.sell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoDAO extends JpaRepository<SellerInfo,Integer> {
    SellerInfo findByOpenid(String openid);
}