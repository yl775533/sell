package com.yl.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.entity.OrderDetail;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.vo.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderMasterDto {
    public static OrderMasterDto converter(OrderForm orderForm){
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        Gson gson = new Gson();

        orderMasterDto.setBuyerName(orderForm.getName());
        orderMasterDto.setBuyerPhone(orderForm.getPhone());
        orderMasterDto.setBuyerAddress(orderForm.getAddress());
        orderMasterDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ExceptionEnum.PARAM_ERROR);
        }
        orderMasterDto.setOrderDetailList(orderDetailList);

        orderMasterDto.setOrderDetailList(orderDetailList);

        return orderMasterDto;
    }
}
