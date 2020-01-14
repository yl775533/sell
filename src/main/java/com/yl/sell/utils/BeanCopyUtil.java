package com.yl.sell.utils;

import org.springframework.beans.BeanUtils;

public final class BeanCopyUtil {

    public static <T> T copy(Object obj, Class<T> clazz) {
        try {
            T result = clazz.newInstance();
            BeanUtils.copyProperties(obj, result);
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
