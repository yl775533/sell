package com.yl.sell.dao;

import com.yl.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Access;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDAOTest {
    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Test
    public void findOne(){
        ProductCategory category = productCategoryDAO.findOne(1);
        System.out.println(category);
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("甜点");
        productCategory.setCategoryType(2);
        productCategoryDAO.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List list= Arrays.asList(2,3,4);
        List categoryTypeIn = productCategoryDAO.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,categoryTypeIn.size());

    }
}