package com.ald.finance.common.enums;

/**
 * Created by liang3.zhang on 2018/5/9.
 */
public enum SMSEnum {

    register("SMS_134321391"), leave("SMS_134321366"), stop("SMS_134326448"), deleteStop(""), deleteLeave(""), change("SMS_1343213380"), buy(""), appointment("");

    private String code;

    SMSEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
