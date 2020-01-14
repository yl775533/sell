package com.yl.sell.service.impl;

import com.yl.sell.dto.CartDto;
import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.entity.OrderDetail;
import com.yl.sell.enums.OrderStatusEnum;
import com.yl.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    @Test
    public void creat() {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setBuyerName("张三");
        orderMasterDto.setBuyerPhone("18868822111");
        orderMasterDto.setBuyerOpenid("ew3euwhd7sjw9diwkq");
        orderMasterDto.setBuyerAddress("慕课网总部");
        CartDto cartDto = new CartDto(1, 2);
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(cartDto, orderDetail);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderMasterDto.setOrderDetailList(orderDetailList);
        OrderMasterDto result = orderMasterService.creat(orderMasterDto);
        log.info("创建订单 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderMasterDto orderMasterDto = orderMasterService.findOne("157183761704554869");
        log.info("查询结果 orderMasterDto={}",orderMasterDto);
        Assert.assertNotNull(orderMasterDto);
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderMasterDto> result = orderMasterService.findList("ew3euwhd7sjw9diwkq", pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderMasterDto orderMasterDto = orderMasterService.findOne("157183761704554869");
        OrderMasterDto cancel = orderMasterService.cancel(orderMasterDto);
        Assert.assertNotEquals(OrderStatusEnum.CANCEL,cancel.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderMasterDto orderMasterDto = orderMasterService.findOne("157183761704554869");
        OrderMasterDto finish = orderMasterService.finish(orderMasterDto);
        Assert.assertNotEquals(OrderStatusEnum.FINISHED,finish.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderMasterDto orderMasterDto = orderMasterService.findOne("157183761704554869");
        OrderMasterDto paid = orderMasterService.paid(orderMasterDto);
        Assert.assertNotEquals(PayStatusEnum.SUCCESS,paid.getPayStatus());
    }
}