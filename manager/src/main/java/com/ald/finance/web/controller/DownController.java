package com.ald.finance.web.controller;

import com.ald.finance.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liang3.zhang on 2018/5/5.
 */
@Controller
@RequestMapping("/fileget")
public class DownController extends BaseController {
    
    @Autowired
    UploadService uploadService;
    
    /**
     * 下载文件
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/images")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        uploadService.download(request,response);
    }
}
