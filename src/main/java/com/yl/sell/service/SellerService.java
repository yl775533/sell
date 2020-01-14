package com.yl.sell.service;

import com.yl.sell.entity.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);
}
