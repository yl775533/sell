package com.yl.sell.controller;

import com.yl.sell.entity.ProductCategory;
import com.yl.sell.entity.ProductForm;
import com.yl.sell.entity.ProductInfo;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.enums.ProductStatusEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.ProductCategoryService;
import com.yl.sell.service.ProductInfoService;
import com.yl.sell.utils.BeanCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询商品列表
     *
     * @param page
     * @param size
     * @param map
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfos = productInfoService.findAll(pageRequest);
        map.put("productInfos", productInfos);
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("product/list", map);
    }

    /**
     * 修改商品
     *
     * @param productId
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) Integer productId,
                              Map<String, Object> map) {
        if (productId != null) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("productCategoryList", productCategoryList);

        return new ModelAndView("product/index", map);

    }

    /**
     * 商品上架
     *
     * @param productId
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") Integer productId,
                               Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error");
        }
        map.put("msg", ExceptionEnum.PRODUCT_STATUS_MODIFI_SUCCESS.getMessage());
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品下架
     *
     * @param productId
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") Integer productId,
                                Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error");
        }
        map.put("msg", ExceptionEnum.PRODUCT_STATUS_MODIFI_SUCCESS.getMessage());
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 提交商品修改表单
     *
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {


        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }


        ProductInfo productInfo = new ProductInfo();

        try {
            if(form.getProductId()!=null){
                productInfo = productInfoService.findOne(form.getProductId());
            }
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ExceptionEnum.PRODUCT_FORM_MODIFI_SUCCESS);
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);

    }


}
