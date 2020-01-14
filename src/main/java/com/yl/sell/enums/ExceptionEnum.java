package com.yl.sell.enums;

import lombok.Getter;

@Getter
public enum  ExceptionEnum {
    PRODUCT_NOT_EXIT(0,"商品不存在"),
    PRODUCT_OUT_OF_STOCK(1,"商品库存不足"),
    ORDER_NOT_EXIST(2,"订单不存在"),
    ORDER_ORDERDETAIL_NOT_EXIST(3,"订单详情不存在"),
    ORDER_FINSHI(4,"该订单已经完结，无法取消"),
    ORDER_CANCEL_AGAIN(5,"该订单已经取消过了，请不要重复取消"),
    ORDER_STATUS_UPDATE_FAILED(6,"订单更新失败"),
    ORDERDETAIL_LIST_IS_NULL(7,"订单无商品"),
    ORDER_STATUS_ERROR(8,"订单状态不正确"),
    ORDER_PAY_STATUS_ERROR(9,"订单支付状态错误"),
    PARAM_ERROR(10,"参数不正确"),
    CART_EMPTY(11,"购物车为空"),
    OPENID_NOT_SAME(12,"该订单不属于当前用户"),
    WECHAT_MP_ERROR(13,"微信公众号错误"),
    ORDER_CANCEL_SUCCESS(14,"订单取消成功"),
    ORDER_FINISH_SUCCESS(15,"订单完结成功"),
    PRODUCT_STATUS_ERROR(16,"商品状态不正确"),
    PRODUCT_STATUS_MODIFI_SUCCESS(17,"商品状态修改成功"),
    PRODUCT_FORM_MODIFI_SUCCESS(18,"商品表单修改成功"),
    CATEGORY_ERROR(19,"类目信息错误"),
    CATEGORY_FORM_MODIFI_SUCCESS(20,"类目表单修改成功"),
    WECHAT_OPEN_ERROR(21,"微信账号错误"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(22,"微信退款错误"),
    SELLER_INFO_ERROR(23,"用户信息错误"),
    ;
    private Integer code;
    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
