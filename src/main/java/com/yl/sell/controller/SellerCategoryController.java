package com.yl.sell.controller;

import com.yl.sell.entity.ProductCategory;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.ProductCategoryService;
import com.yl.sell.service.ProductInfoService;
import com.yl.sell.utils.BeanCopyUtil;
import com.yl.sell.vo.form.CategoryForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.POST;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> productCategoryList = productCategoryService.findAll();

        map.put("productCategoryList", productCategoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId!=null){
            ProductCategory category = productCategoryService.findOne(categoryId);
            map.put("category",category);
        }

        return new ModelAndView("category/index");

    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        //如果表单有问题，跳到错误页面
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory productCategory = new ProductCategory();
        //如果categoryId不为空，为修改类目

        try {
            if (categoryForm.getCategoryId()!=null){
                productCategory=productCategoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            productCategoryService.saveCategory(productCategory);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        map.put("msg", ExceptionEnum.CATEGORY_FORM_MODIFI_SUCCESS);
        map.put("url", "/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
