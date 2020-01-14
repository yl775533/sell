package com.yl.sell.service.impl;

import com.yl.sell.dao.ProductCategoryDAO;
import com.yl.sell.entity.ProductCategory;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        ProductCategory category = productCategoryDAO.findOne(categoryId);
        if (category==null){
            throw new SellException(ExceptionEnum.CATEGORY_ERROR);
        }
        return category;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDAO.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDAO.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory saveCategory(ProductCategory productCategory) {
        return productCategoryDAO.save(productCategory);
    }
}
