package com.ald.finance.common.web;

/**
 * 业务异常类
 * 
 * @author jlf
 * @created 17/2/9
 * @since v1.0
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private Object data;
    
    private int code;
    
    /**
     * 错误信息
     */
    private String message;
    
    public BusinessException() {
        super();
    }
    
    public BusinessException(String message) {
        super();
        this.code = 1;
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "BusinessException{" + "data=" + data + ", code=" + code + ", message='" + message + '\'' + '}';
    }
}
