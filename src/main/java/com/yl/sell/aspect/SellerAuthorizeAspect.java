package com.yl.sell.aspect;


import com.yl.sell.annotation.Auth;
import com.yl.sell.constant.RedisConstant;
import com.yl.sell.exception.SellerAuthorizeException;
import com.yl.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

   // @Pointcut("execution(public * com.yl.sell.controller.Seller*.*(..))" +
            //"&& !execution(public * com.yl.sell.controller.SellerUserController.*(..))")
    //public void verify() {

    //}

    @Pointcut("@annotation(com.yl.sell.annotation.Auth)")
    public void verify() {

    }

    @Before("verify() && @annotation(auth)")
    public void doverify(Auth auth){
//        String qx = auth.qx();
//        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        //查询cookie
//        Cookie cookie = CookieUtil.get(request, "token");
//        if (cookie==null){
//            log.warn("cookie中查不到token");
//            throw new SellerAuthorizeException();
//        }
//        // 去裤里面拿user信息
//        // 比较接口所需权限与用户权限


        //
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie==null){
            log.warn("cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
