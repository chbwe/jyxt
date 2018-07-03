package com.ald.finance.common.web;

import java.io.Serializable;

/**
 * Created by liang3.zhang on 2018/1/23.
 */
public class ResponseModel<T> implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 4525431233220956770L;
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    
    /**
     * 返回结果
     */
    private T data;
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public ResponseModel code(int code) {
        this.code = code;
        return this;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public ResponseModel message(String message) {
        this.message = message;
        return this;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public ResponseModel data(T data) {
        this.data = data;
        return this;
    }
}
