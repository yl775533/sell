package com.yl.sell.exception;

import com.yl.sell.enums.ExceptionEnum;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code=exceptionEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
