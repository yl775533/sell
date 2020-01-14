package com.yl.sell.service.impl;

import com.yl.sell.converter.OrderMaster2OrderMasterDto;
import com.yl.sell.dao.OrderDetailDAO;
import com.yl.sell.dao.OrderMasterDAO;
import com.yl.sell.dto.CartDto;
import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.entity.OrderDetail;
import com.yl.sell.entity.OrderMaster;
import com.yl.sell.entity.ProductInfo;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.enums.OrderStatusEnum;
import com.yl.sell.enums.PayStatusEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.OrderMasterService;
import com.yl.sell.service.ProductInfoService;
import com.yl.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Override
    @Transactional
    public OrderMasterDto creat(OrderMasterDto orderMasterDto) {

        BigDecimal orderAmount = new BigDecimal(0);
        String orderId = KeyUtil.genUniqueKey();

        //查询商品（数量，价格）
        for (OrderDetail orderDetails : orderMasterDto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetails.getProductId());
            if (productInfo == null) {
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIT);
            }

            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetails.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetails.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetails);
            orderDetailDAO.save(orderDetails);
        }

        //订单入库
        OrderMaster orderMaster = new OrderMaster();

        orderMasterDto.setOrderId(orderId);


        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterDAO.save(orderMaster);

        //扣库存
        List<CartDto> cartDtoList = orderMasterDto.getOrderDetailList().stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);
        return orderMasterDto;
    }

    @Override
    public OrderMasterDto findOne(String orderId) {

        OrderMaster orderMaster = orderMasterDAO.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailDAO.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ExceptionEnum.ORDER_ORDERDETAIL_NOT_EXIST);
        }

        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setOrderDetailList(orderDetailList);
        BeanUtils.copyProperties(orderMaster, orderMasterDto);


        return orderMasterDto;
    }

    @Override
    public Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterDAO.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderMasterDto> orderMasterDtoList = OrderMaster2OrderMasterDto.convertList(orderMasterPage.getContent());

        Page<OrderMasterDto> orderMasterDtos = new PageImpl<OrderMasterDto>(orderMasterDtoList, pageable, orderMasterPage.getTotalElements());
        return orderMasterDtos;
    }

    @Override
    @Transactional
    public OrderMasterDto cancel(OrderMasterDto orderMasterDto) {

        //判断订单状态
        if (orderMasterDto.getOrderStatus() == OrderStatusEnum.FINISHED.getCode()) {
            throw new SellException(ExceptionEnum.ORDER_FINSHI);
        }
        if (orderMasterDto.getOrderStatus() == OrderStatusEnum.CANCEL.getCode()) {
            throw new SellException(ExceptionEnum.ORDER_CANCEL_AGAIN);
        }
        //修改订单状态
        orderMasterDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateResul = orderMasterDAO.save(orderMaster);
        if (updateResul == null) {
            log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
            throw new SellException(ExceptionEnum.ORDER_STATUS_UPDATE_FAILED);
        }

        //返还库存
        if (CollectionUtils.isEmpty(orderMasterDto.getOrderDetailList())) {
            log.info("【订单无商品】，orderMasterDto={}", orderMasterDto);
            throw new SellException(ExceptionEnum.ORDERDETAIL_LIST_IS_NULL);
        }

        List<CartDto> cartDtoList = orderMasterDto.getOrderDetailList().stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productInfoService.increaseStock(cartDtoList);

        //如果已支付，需要退款
        if (orderMasterDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderMasterDto;
    }

    @Override
    @Transactional
    public OrderMasterDto finish(OrderMasterDto orderMasterDto) {
        //判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderMasterDto.getOrderId(), orderMasterDto.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMasterDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDto, orderMaster);

        OrderMaster updateOrderMaster = orderMasterDAO.save(orderMaster);
        if (updateOrderMaster == null) {
            log.error("【完结订单】完结订单失败，orderMaster={}", orderMaster);
            throw new SellException(ExceptionEnum.ORDER_STATUS_UPDATE_FAILED);
        }
        return orderMasterDto;
    }

    @Override
    @Transactional
    public OrderMasterDto paid(OrderMasterDto orderMasterDto) {
        //判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态不正确，orderId={},orderStatus={}", orderMasterDto.getOrderId(), orderMasterDto.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderMasterDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】支付状态不正确，orderId={},orderStatus={}", orderMasterDto.getOrderId(), orderMasterDto.getPayStatus());
            throw new SellException(ExceptionEnum.ORDER_PAY_STATUS_ERROR);
        }
        //支付订单，修改支付状态
        orderMasterDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateOrderMaster = orderMasterDAO.save(orderMaster);
        if (updateOrderMaster == null) {
            log.error("【支付订单】支付订单失败，orderMaster={}", orderMaster);
            throw new SellException(ExceptionEnum.ORDER_STATUS_UPDATE_FAILED);
        }

        //扣款
        if (orderMasterDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            //TODO
        }
        return orderMasterDto;
    }

    @Override
    public Page<OrderMasterDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterDAO.findAll(pageable);

        List<OrderMasterDto> orderMasterDtoList = OrderMaster2OrderMasterDto.convertList(orderMasters.getContent());

        Page<OrderMasterDto> orderMasterDtos = new PageImpl<OrderMasterDto>(orderMasterDtoList, pageable, orderMasters.getTotalElements());
        return orderMasterDtos;
    }
}
