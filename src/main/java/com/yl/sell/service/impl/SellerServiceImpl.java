package com.yl.sell.service.impl;

import com.yl.sell.dao.SellerInfoDAO;
import com.yl.sell.entity.SellerInfo;
import com.yl.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoDAO sellerInfoDAO;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDAO.findByOpenid(openid);
    }
}
