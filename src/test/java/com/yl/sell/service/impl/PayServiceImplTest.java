package com.yl.sell.service.impl;

import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.yl.sell.dto.OrderMasterDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;
    @Test
    public void create() {
        //OrderMasterDto orderMasterDto = new OrderMasterDto();
        //payService.create(orderMasterDto);
    }
}