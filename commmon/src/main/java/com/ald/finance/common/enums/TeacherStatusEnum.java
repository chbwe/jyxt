package com.ald.finance.common.enums;

/**
 * Created by zhangliang on 2018/5/2.
 */
public enum TeacherStatusEnum {
    Deft(0, "默认"), Course(1, "已导入课程");
    
    private String message;
    private Integer code;
    
    TeacherStatusEnum(Integer code, String roleName) {
        this.message = message;
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
}
