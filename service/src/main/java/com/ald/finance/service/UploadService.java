package com.ald.finance.service;

import com.ald.finance.dal.entity.Upload;
import com.ald.finance.dal.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by liang3.zhang on 2018/5/9.
 */
@Service
public class UploadService {
    
    @Autowired
    UploadRepository uploadRepository;

    
    /**
     * 保存文件
     * 
     * @param content
     * @return
     */
    public Upload save(String content) {
        Upload upload = new Upload();
        upload.setUuid(UUID.randomUUID().toString());
        upload.setContent(content);
        uploadRepository.save(upload);
        return upload;
    }
    
    /**
     * 下载文件
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String uuid = request.getParameter("uuid");
        Upload upload = uploadRepository.findOne(uuid);
        OutputStream out = response.getOutputStream();
        if (upload == null)
            return;
        out.write(Base64Utils.decode(upload.getContent().getBytes("UTF-8")));
        out.flush();
    }
}
