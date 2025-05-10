package com.dawnmoon.big_event.controller;

import com.dawnmoon.big_event.pojo.Response;
import com.dawnmoon.big_event.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    public Response<?> upload(String description, MultipartFile file) throws IOException {

        if(description == null || description.isEmpty()){
            return Response.error(1, "描述或文件为空");
        }
        String url = uploadService.upload(description, file);
        if (url == null || url.isEmpty()) {
            return Response.error(1, "上传失败");
        }
        return Response.success("上传文件成功", url);
    }
}
