package com.yl.sell.controller;

import com.yl.sell.config.ProjectUrlConfig;
import com.yl.sell.constant.RedisConstant;
import com.yl.sell.entity.SellerInfo;
import com.yl.sell.enums.ExceptionEnum;
import com.yl.sell.exception.SellException;
import com.yl.sell.service.SellerService;
import com.yl.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                      HttpServletResponse response,
                      Map<String,Object> map){

        //openid和数据库做匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo==null){
            map.put("msg", ExceptionEnum.SELLER_INFO_ERROR);
            map.put("url","/order/list");
            return new ModelAndView("common/error",map);
        }

        //设置token去redies
        String token= UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;

        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);


        //设置token去cookie
        CookieUtil.set(response,"token",token,expire);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout (HttpServletRequest request,
                                HttpServletResponse response,
                                Map<String,Object> map){
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie!=null){
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //清除cookie
            CookieUtil.set(response,"token",null,0);
        }
        map.put("msg","成功");
        map.put("url","/order/list");
        return new ModelAndView("common/success",map);
    }
}
