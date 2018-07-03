package com.ald.finance.common.enums;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
public enum CourseRecordEnum {

    none(0, "未开始"), cls(1, "上课"), leave(-1, "请假"), stop(-2, "老师停课"), holiday(-9, "节假日");

    private Integer code;
    private String text;

    CourseRecordEnum(Integer code, String text) {
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
