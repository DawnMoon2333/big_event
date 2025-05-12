package com.dawnmoon.big_event.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${jwt.expirationTime}")
    Integer expirationTime;

    private final String secret; // 使用 final 确保 secret 不会被意外修改
    public JWTUtil(@Value("${password.secret}") String secret) {
        this.secret = secret;
    }

    public String genToken(Map<String, Object> claims) { // 非静态方法

        //Map<String, Object> claims = new HashMap<>();
        //claims.put("id", 1);
        //claims.put("username", "zhangsan");
        return JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime*60*1000L))
                .sign(Algorithm.HMAC256(secret));
    }

    public Map<String, Object> parseToken(String token) { // 非静态方法
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("user")
                .asMap();
    }

    public Integer getCurUserId(){
        Map<String, Object> map = ThreadLocalUtil.get();
        return (Integer) map.get("id");
    }
}
