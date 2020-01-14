package com.yl.sell.service;

import com.yl.sell.dto.OrderMasterDto;

public interface BuyerService {
    OrderMasterDto findOrderOne(String openid,String orderId);

    OrderMasterDto cancelOrderOne(String openid,String orderId);
}
