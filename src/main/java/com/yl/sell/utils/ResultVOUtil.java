package com.yl.sell.utils;


import com.yl.sell.vo.ResultVo;

/**
 * Created by yl
 * 2017-05-15 00:22
 */

public class ResultVOUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setDate(object);
        resultVO.setCode(0);
        resultVO.setMes("成功");
        return resultVO;
    }

    public static ResultVo success() {
        return success(null);
    }

    public static ResultVo fail(Integer code, String msg) {
        ResultVo resultVO = new ResultVo();
        resultVO.setCode(code);
        resultVO.setMes(msg);
        return resultVO;
    }
}
