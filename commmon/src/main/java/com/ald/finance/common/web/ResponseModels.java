package com.ald.finance.common.web;

/**
 * ResponseModels
 *
 * @author jlf
 * @created 17/2/6
 * @since v1.0
 */
public class ResponseModels {
    
    /**
     * 返回正常信息
     * 
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseModel<T> ok(T data) {
        return new ResponseModel().code(0).data(data);
    }
    
    /**
     * 参数错误
     * 
     * @param message
     * @return
     */
    public static ResponseModel paramValidException(String message) {
        return new ResponseModel().code(2).message(message);
    }
}
