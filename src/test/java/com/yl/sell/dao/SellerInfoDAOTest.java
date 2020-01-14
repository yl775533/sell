package com.yl.sell.dao;

import com.yl.sell.entity.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoDAOTest {

    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerInfoDAO.findByOpenid("yl811743850");
        Assert.assertNotNull(sellerInfo);
    }
}