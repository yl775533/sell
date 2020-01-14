package com.yl.sell.dao;

import com.yl.sell.entity.OrderMaster;
import com.yl.sell.enums.OrderStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDAOTest {
    @Autowired
    private OrderMasterDAO orderMasterDAO;
    private final String OPENID="921";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("蒋家小区");
        orderMaster.setBuyerName("小小");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerPhone("15673927738");
        orderMaster.setOrderAmount(new BigDecimal(13));
        orderMaster.setOrderId("2");
        OrderMaster result = orderMasterDAO.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0,3);
        Page<OrderMaster> result = orderMasterDAO.findByBuyerOpenid(OPENID,pageRequest);
        System.out.println(result.getTotalElements());
    }


}