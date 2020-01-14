package com.yl.sell.converter;

import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderMasterDto {
    public static OrderMasterDto convert(OrderMaster orderMaster){
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        BeanUtils.copyProperties(orderMaster,orderMasterDto);
        return orderMasterDto;
    }

    public static List<OrderMasterDto> convertList(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(OrderMaster2OrderMasterDto::convert).collect(Collectors.toList());
    }
}
