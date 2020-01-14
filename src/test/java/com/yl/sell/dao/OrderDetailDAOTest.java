package com.yl.sell.dao;

import com.yl.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDAOTest {

    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(2);
        orderDetail.setOrderId("1");
        orderDetail.setProductId(1);
        orderDetail.setProductName("红烧肉");
        orderDetail.setProductIcon("jhdsjhjs");
        orderDetail.setProductQuantity(1);
        orderDetail.setProductPrice(new BigDecimal(12));
        OrderDetail result = orderDetailDAO.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = orderDetailDAO.findByOrderId("1");
        Assert.assertNotEquals(0,result.size());
    }
}