package com.yl.sell.vo;

import lombok.Data;

/**
 * 请求的最外层对象
 */
@Data
public class ResultVo<T> {

    private Integer code;

    private String mes;

    private T date;
}
