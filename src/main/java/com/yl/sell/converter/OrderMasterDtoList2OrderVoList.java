package com.yl.sell.converter;

import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.vo.resp.OrderVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterDtoList2OrderVoList {

    public static OrderVo convert(OrderMasterDto orderMasterDto){
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(orderMasterDto,orderVo);
        return orderVo;
    }

    public static List<OrderVo> convertList(List<OrderMasterDto> orderMasterDtoList){
        return orderMasterDtoList.stream().map(e->
                convert(e)
        ).collect(Collectors.toList());



    }


    }

