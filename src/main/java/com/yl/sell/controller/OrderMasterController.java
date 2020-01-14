package com.yl.sell.controller;


import com.yl.sell.converter.OrderForm2OrderMasterDto;
import com.yl.sell.converter.OrderMasterDtoList2OrderVoList;
import com.yl.sell.dto.OrderMasterDto;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.BuyerService;
import com.yl.sell.vo.form.OrderForm;
import com.yl.sell.service.OrderMasterService;
import com.yl.sell.utils.ResultVOUtil;
import com.yl.sell.vo.ResultVo;
import com.yl.sell.vo.resp.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class OrderMasterController {

    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVo<Map<String, String>> creat(@Valid OrderForm orderForm,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确。orderForm={}", orderForm);
            throw new SellException(ExceptionEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }


        OrderMasterDto orderMasterDto = OrderForm2OrderMasterDto.converter(orderForm);

        if (CollectionUtils.isEmpty(orderMasterDto.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ExceptionEnum.CART_EMPTY);
        }

        OrderMasterDto creat = orderMasterService.creat(orderMasterDto);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", creat.getOrderId());


        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVo<List<OrderVo>> list(@RequestParam("openid") String opeanid,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(opeanid)) {
            log.error("【查询订单列表】opeanid不能为空");
            throw new SellException(ExceptionEnum.PARAM_ERROR);
        }
        Page<OrderMasterDto> masterDtos = orderMasterService.findList(opeanid, new PageRequest(page, size));
        List<OrderMasterDto> result = masterDtos.getContent();
        List<OrderVo> orderVoList = OrderMasterDtoList2OrderVoList.convertList(result);

        return ResultVOUtil.success(orderVoList);
    }

    //查询订单详情
    @GetMapping("/detail")
    public ResultVo<OrderMasterDto> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {

        OrderMasterDto orderOne = buyerService.findOrderOne(openid, orderId);

        return ResultVOUtil.success(orderOne);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {


        buyerService.cancelOrderOne(openid, orderId);
        return ResultVOUtil.success();
    }
}
