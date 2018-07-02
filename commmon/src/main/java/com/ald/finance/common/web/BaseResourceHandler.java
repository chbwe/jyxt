package com.ald.finance.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class BaseResourceHandler {
    
    private static final Logger log = LoggerFactory.getLogger(BaseResourceHandler.class);
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * 处理业务异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     * @exception IOException
     */
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(HttpServletRequest request, HttpServletResponse response, BusinessException e)
        throws IOException {
        log.warn("Catch BusinessException,code={},message={}", e.getCode(), e.getMessage(), e);
        return ResponseEntity.ok(ResponseModels.paramValidException(e.getMessage()));
    }
    
    /**
     * 统一处理所有底层代码抛出的异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     * @exception IOException
     */
    @ExceptionHandler()
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception e)
        throws IOException {
        log.error("Catch Exception,message={}", e.getMessage(), e);
        return ResponseEntity.ok(ResponseModels.paramValidException(e.getMessage()));
    }
}
