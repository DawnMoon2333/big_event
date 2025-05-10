package com.dawnmoon.big_event.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;

@Component
public class PasswordUtil {

    @Value("${password.salt}")
    private String salt;

    public String SHA256Encrypt(String password){
        String Salted = password + salt;
        String Base64Encoded = Base64.getEncoder().encodeToString(Salted.getBytes(StandardCharsets.UTF_8));

        try {
            // 计算sha256哈希值
            MessageDigest md = MessageDigest.getInstance("SHA256");
            byte[] digest = md.digest(Base64Encoded.getBytes(StandardCharsets.UTF_8));

            // sha256哈希值转16进制字符串
            HexFormat formatter = HexFormat.of();
            return formatter.formatHex(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
