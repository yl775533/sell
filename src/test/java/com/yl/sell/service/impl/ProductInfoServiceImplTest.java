package com.yl.sell.service.impl;

import com.yl.sell.entity.ProductInfo;
import com.yl.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne(343);
        Assert.assertEquals(Integer.valueOf(343),productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productInfoService.findUpAll();
        Assert.assertNotEquals(0,upAll);
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        System.out.println(productInfos.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(345);
        productInfo.setProductName("红烧肉");
        productInfo.setProductDescription("好吃");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("sdfsdfsfsdf");
        productInfo.setProductStock(10);
        productInfo.setCategoryType(1);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale() {
        ProductInfo productInfo = productInfoService.onSale(1);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void offSale() {
        ProductInfo productInfo = productInfoService.offSale(1);
        Assert.assertNotNull(productInfo);
    }
}