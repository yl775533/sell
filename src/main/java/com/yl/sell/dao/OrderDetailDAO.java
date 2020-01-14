package com.yl.sell.dao;

import com.yl.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId (String orderId);


}