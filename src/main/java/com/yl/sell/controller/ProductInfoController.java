package com.yl.sell.controller;

import com.yl.sell.annotation.Auth;
import com.yl.sell.vo.ProductInfoListVo;
import com.yl.sell.vo.ProductVo;
import com.yl.sell.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/buyer/product")
public class ProductInfoController {

    @GetMapping("/list")
    @Auth(qx = "admin")
    public ResultVo list(){
        ResultVo resultVo = new ResultVo();
        ProductVo productVo = new ProductVo();
        ProductInfoListVo productInfoListVo = new ProductInfoListVo();
        productVo.setProductInfoList(Arrays.asList(productInfoListVo));
        resultVo.setDate(Arrays.asList(productVo));

        resultVo.setCode(0);
        resultVo.setMes("成功");
        return resultVo;
    }
}
