package com.dawnmoon.big_event.utils;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Component
public class ObjectStorageUtil {

    @Value("${huaweistorage.ak}")
    private String ak;
    @Value("${huaweistorage.sk}")
    private String sk;
    @Value("${huaweistorage.endpoint}")
    private String endPoint;
    @Value("${huaweistorage.access-domain}")
    private String accessDomain;

    public String  upload(MultipartFile file, String filename) throws IOException {

        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        try {
            // 文件上传
            // localfile 为待上传的本地文件路径，需要指定到具体的文件名
            PutObjectRequest request = new PutObjectRequest();
            request.setBucketName("big-event-dawnmoon");
            request.setObjectKey("files/"+filename);
            request.setFile(multipartToFile(file));
            PutObjectResult result = obsClient.putObject(request);
            //System.out.println("putObject successfully");
            return "https://"+accessDomain+result.getObjectKey();
        } catch (ObsException e) {
            System.out.println("putObject failed");
            // 请求失败,打印http状态码
            System.out.println("HTTP Code:" + e.getResponseCode());
            // 请求失败,打印服务端错误码
            System.out.println("Error Code:" + e.getErrorCode());
            // 请求失败,打印详细错误信息
            System.out.println("Error Message:" + e.getErrorMessage());
            // 请求失败,打印请求id
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("putObject failed");
            // 其他异常信息打印
            e.printStackTrace();
        }
        return null;
    }

    public File multipartToFile(MultipartFile multipartFile) throws IOException {
        // 用临时文件创建一个 File 对象
        File tempFile = File.createTempFile("upload_", multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile); // 把内容写入文件
        tempFile.deleteOnExit(); // JVM 退出时自动删除
        return tempFile;
    }
}
