package com.dawnmoon.big_event.interceptor;

import com.dawnmoon.big_event.utils.JWTUtil;
import com.dawnmoon.big_event.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JWTUtil jwtUtil;

    @Autowired
    public LoginInterceptor(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        try {
            Map<String, Object> claims = jwtUtil.parseToken(token);
            // 把解析后的数据存储到ThreadLocal中，避免其他方法重复解析token
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清楚ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
