package com.yl.sell.service;

import com.yl.sell.dto.OrderMasterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderMasterService {
    /**
     * 创建订单
     */
    OrderMasterDto creat(OrderMasterDto orderMasterDto);

    /**
     * 根据订单id查询单个订单
     */
    OrderMasterDto findOne(String orderId);

    /**
     * 根据订单opeanId分页查询订单列表
     */
    Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderMasterDto cancel(OrderMasterDto orderMasterDto);

    /**
     * 完结订单
     */
    OrderMasterDto finish(OrderMasterDto orderMasterDto);

    /**
     * 支付订单
     */
    OrderMasterDto paid(OrderMasterDto orderMasterDto);

    Page<OrderMasterDto> findList(Pageable pageable);
}
