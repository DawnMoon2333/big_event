package com.dawnmoon.big_event.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String upload(String description, MultipartFile file) throws IOException;
}
