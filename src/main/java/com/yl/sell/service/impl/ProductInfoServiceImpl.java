package com.yl.sell.service.impl;

import com.yl.sell.dao.ProductInfoDAO;
import com.yl.sell.dto.CartDto;
import com.yl.sell.entity.ProductInfo;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.enums.ProductStatusEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Override
    public ProductInfo findOne(Integer productId) {
        return productInfoDAO.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDAO.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDAO.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = productInfoDAO.findOne(cartDto.getProductId());
            if (productInfo == null) {
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIT);
            }

            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (result < 0) {
                throw new SellException(ExceptionEnum.PRODUCT_OUT_OF_STOCK);
            }

            productInfo.setProductStock(result);
            productInfoDAO.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = productInfoDAO.findOne(cartDto.getProductId());
            if (productInfo == null) {
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIT);
            }

            Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();

            productInfo.setProductStock(result);
            productInfoDAO.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(Integer productId) {
        ProductInfo productInfo = productInfoDAO.findOne(productId);
        if(productInfo==null){
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ExceptionEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoDAO.save(productInfo);
        return productInfo;
    }

    @Override
    public ProductInfo offSale(Integer productId) {
        ProductInfo productInfo = productInfoDAO.findOne(productId);
        if(productInfo==null){
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ExceptionEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoDAO.save(productInfo);
        return productInfo;
    }
}


