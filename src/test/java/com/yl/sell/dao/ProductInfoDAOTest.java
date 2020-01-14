package com.yl.sell.dao;

import com.yl.sell.entity.ProductInfo;
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
public class ProductInfoDAOTest {

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(343);
        productInfo.setProductName("黄焖鸡");
        productInfo.setProductDescription("贼好吃");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("sdfsfsdf");
        productInfo.setProductStock(10);
        productInfo.setCategoryType(1);
        ProductInfo result = productInfoDAO.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoDAO.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
}