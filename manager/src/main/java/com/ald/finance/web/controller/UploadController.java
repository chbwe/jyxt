package com.ald.finance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ald.finance.dal.entity.Upload;
import com.ald.finance.common.web.ResponseModel;
import com.ald.finance.common.web.ResponseModels;
import com.ald.finance.service.UploadService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Created by Wendel on 2017/4/27.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
    
    @Autowired
    UploadService uploadService;
    
    @RequestMapping("/images")
    @ResponseBody
    public ResponseModel<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        Upload upload = uploadService.save(Base64.encode(file.getBytes()));
        return ResponseModels.ok("/fileget/images?uuid=" + upload.getUuid());
    }
}
