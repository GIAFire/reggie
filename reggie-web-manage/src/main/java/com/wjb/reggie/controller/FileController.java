package com.wjb.reggie.controller;

import com.wjb.reggie.common.OssTemplate;
import com.wjb.reggie.common.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.PortUnreachableException;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private OssTemplate ossTemplate;

    // 文件上传
    @PostMapping("/common/upload")
    public ResultInfo upload(MultipartFile file) throws IOException { // 1.接收前端文件对象
        // 2.调用阿里云oss上传
        String picUrl = ossTemplate.upload(file.getOriginalFilename(), file.getInputStream());
        // log.info("图片上传地址："+picUrl+);
        log.info("图片上传地址：{}",picUrl);
        // 3.返回图片url地址
        return ResultInfo.success(picUrl);

    }
}
