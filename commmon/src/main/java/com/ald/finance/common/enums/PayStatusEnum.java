package com.ald.finance.common.enums;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
public enum PayStatusEnum {

    none(0, "未支付"), pay(1, "已支付");

    private Integer code;
    private String text;

    PayStatusEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getText() {
        return text;
    }
}
