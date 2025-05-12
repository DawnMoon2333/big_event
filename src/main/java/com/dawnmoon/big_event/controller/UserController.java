package com.dawnmoon.big_event.controller;

import com.dawnmoon.big_event.pojo.Response;
import com.dawnmoon.big_event.pojo.User;
import com.dawnmoon.big_event.service.UserService;
import com.dawnmoon.big_event.utils.JWTUtil;
import com.dawnmoon.big_event.utils.PasswordUtil;
import com.dawnmoon.big_event.utils.ThreadLocalUtil;
import com.dawnmoon.big_event.utils.ValidationUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.expirationTime}")
    Integer expirationTime;

    private final UserService userService;
    private final ValidationUtil validationUtil;
    private final PasswordUtil passwordUtil;
    private final JWTUtil jwtHelper;
    private final StringRedisTemplate stringRedisTemplate;

    // 构造器注入
    @Autowired
    public UserController(UserService userService, ValidationUtil validationUtil, PasswordUtil passwordUtil, JWTUtil jwtUtil, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.validationUtil = validationUtil;
        this.passwordUtil = passwordUtil;
        this.jwtHelper = jwtUtil;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/register")
    public Response<?> register(String username, String password, String nickname){

        // 参数验证
        var validationResult = validationUtil.register(username, password, nickname);
        if (validationResult.getLeft() != 0) {
            return Response.error(validationResult.getLeft(), validationResult.getRight());
        }

        userService.register(username, password, nickname);
        return Response.success("注册成功");
    }

    @PostMapping("/login")
    public Response<?> login(String username, String password){

        // 检查用户名是否存在
        User user = userService.findByUsername(username);
        if(user==null){
            return Response.error(1, "用户名不存在");
            // 实际开发应该改为用户名或密码错误
        }

        String encryptedPassword = passwordUtil.SHA256Encrypt(password);

        if (encryptedPassword == null) {
            return Response.error(1, "令牌无效");
        }
        else if(!encryptedPassword.equals(user.getPassword())){
            return Response.error(1, "密码错误");
            // 实际开发应该改为用户名或密码错误
        }
        else{
            // 构建载荷用于生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            var jwt = jwtHelper.genToken(claims);

            // 向redis存入数据
            var redisOperations = stringRedisTemplate.opsForValue();
            redisOperations.set(jwt, jwt, expirationTime, TimeUnit.MINUTES); // 有效期15分钟
            return Response.success("登陆成功", jwt);
        }
    }

    @GetMapping("/getInfo")
    public Response<User> getInfo(){

        // 从ThreadLocal获取用户信息
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        return Response.success("获取用户信息成功", user);
    }

    @PutMapping("/updateBasic")
    public Response<?> updateBasic(@RequestBody User user){

        // RequestBody会读取前端传入的json格式的参数，将其转为User的对象，并赋值对应的值
        // id验证
        Map<String, Object> claims = ThreadLocalUtil.get();
        int tokenId =(int) claims.get("id");
        if (tokenId != (int)user.getId()) {
            return Response.error(1, "非法请求，id不一致");
        }

        // 参数验证
        var validationResult = validationUtil.updateBasic(user.getUsername(), user.getNickname());
        if (validationResult.getLeft() != 0) {
            return Response.error(validationResult.getLeft(), validationResult.getRight());
        }

        userService.updateBasic(user);
        return Response.success("更新用户信息成功", user);
    }

    @PatchMapping("/updatePic")
    public Response<?> updatePic(@RequestParam @URL String picUrl){
        userService.updatePic(picUrl);
        return Response.success("更新用户头像成功");
    }

    @PatchMapping("/updatePwd")
    public Response<?> updatePwd(@RequestBody Map<String, String> param, @RequestParam("Authorization") String token){
        String oldPwd = param.get("oldPwd");
        String newPwd = param.get("newPwd");
        String rePwd = param.get("rePwd");

        // 三个值非空
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Response.error(1, "缺少必要的参数");
        }

        // 校验旧密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User curUser = userService.findByUsername(username);
        if (!curUser.getPassword().equals(passwordUtil.SHA256Encrypt(oldPwd))){
            return Response.error(1, "旧密码错误");
        }

        // 两次新密码输入一致
        if (!newPwd.equals(rePwd)){
            return Response.error(1, "两次密码输入不一致");
        }

        // 新密码符合要求
        var valPwdResult = validationUtil.passwordValid(newPwd);
        if (valPwdResult.getLeft() != 0) {
            return Response.error(valPwdResult.getLeft(), valPwdResult.getRight());
        }

        userService.updatePwd(newPwd);
        // 删除redis token
        stringRedisTemplate.opsForValue().getOperations().delete(token);
        return Response.success("更新用户密码成功");
    }
}
