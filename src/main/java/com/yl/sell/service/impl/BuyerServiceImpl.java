package com.yl.sell.service.impl;

import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.BuyerService;
import com.yl.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderMasterDto findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderMasterDto cancelOrderOne(String openid, String orderId) {
        OrderMasterDto orderMasterDto = orderMasterService.findOne(orderId);
        if (orderMasterDto == null) {
            log.error("【取消订单】查不到该订单，orderId={}",orderId);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        if (!orderMasterDto.getBuyerOpenid().equals(openid)) {
            log.error("【查询订单】买家id不一致,openid={},orderMasterDto={}",openid,orderMasterDto);
            throw new SellException(ExceptionEnum.OPENID_NOT_SAME);
        }
        OrderMasterDto masterDto = orderMasterService.cancel(orderMasterDto);
        return masterDto;
    }

    private OrderMasterDto checkOrderOwner(String openid, String orderId) {
        OrderMasterDto orderMasterDto = orderMasterService.findOne(orderId);
        if (orderMasterDto == null) {
            return null;
        }
        if (!orderMasterDto.getBuyerOpenid().equals(openid)) {
            log.error("【查询订单】买家id不一致,openid={},orderMasterDto={}",openid,orderMasterDto);
            throw new SellException(ExceptionEnum.OPENID_NOT_SAME);
        }
        return orderMasterDto;
    }
}
