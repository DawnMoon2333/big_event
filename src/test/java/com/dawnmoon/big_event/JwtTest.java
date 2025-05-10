package com.dawnmoon.big_event;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){

        // 构建载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "zhangsan");

        String token =JWT.create()
                .withClaim("user", claims)  // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*3))  // 3小时后过期
                .sign(Algorithm.HMAC256("jiamimiyao"));  // 指定加密算法，配置加密密钥

        System.out.println(token);

    }

    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0sImV4cCI6MTc0MTE0NDkwNH0.E17hmjNr43z_962zK6szVD93i2kKH8s_udn3Gtfol5A";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("jiamimiyao")).build();
        DecodedJWT decodeJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodeJWT.getClaims();
        System.out.println(claims.get("user"));

    }
}
