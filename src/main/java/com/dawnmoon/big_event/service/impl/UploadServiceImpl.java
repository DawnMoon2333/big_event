package com.dawnmoon.big_event.service.impl;

import com.dawnmoon.big_event.mapper.UploadMapper;
import com.dawnmoon.big_event.service.UploadService;
import com.dawnmoon.big_event.utils.JWTUtil;
import com.dawnmoon.big_event.utils.ObjectStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    private final JWTUtil jwtUtil;
    private final UploadMapper uploadMapper;
    private final ObjectStorageUtil objectStorageUtil;

    @Autowired
    public UploadServiceImpl(JWTUtil jwtUtil, UploadMapper uploadMapper, ObjectStorageUtil objectStorageUtil) {
        this.jwtUtil = jwtUtil;
        this.uploadMapper = uploadMapper;
        this.objectStorageUtil = objectStorageUtil;
    }

    @Override
    public String upload(String description, MultipartFile file) throws IOException {

        String oriFileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString()+oriFileName.substring(oriFileName.lastIndexOf("."));
        String url = objectStorageUtil.upload(file, newFileName);
        var id =jwtUtil.getCurUserId();
        var time = LocalDateTime.now();
        uploadMapper.upload(id, description, url, time);
        return url;
    }
}
