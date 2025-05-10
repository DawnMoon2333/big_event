package com.dawnmoon.big_event.utils;

import com.dawnmoon.big_event.pojo.User;
import com.dawnmoon.big_event.service.UserService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class ValidationUtil {

    private final UserService userService;

    @Autowired
    public ValidationUtil(UserService userService) {
        this.userService = userService;
    }

    public Pair<Integer, String> usernameValid(String username) {
        if (!StringUtils.hasLength(username)) {
            return Pair.of(1, "用户名不能为空");
        }
        if (username.length() < 6 || username.length() > 20) {
            return Pair.of(1, "用户名长度必须在6-20个字符之间");
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            return Pair.of(0, "可以注册"); // 或任何表示成功的 code 和 message
        } else {
            return Pair.of(1, "用户名重复");
        }
    }

    public Pair<Integer, String> passwordValid(String password){
        if (!StringUtils.hasLength(password)) {
            return Pair.of(1, "密码不能为空");
        }
        if (password.length() < 6 || password.length() > 20) {
            return Pair.of(1, "密码长度必须在6-20个字符之间");
        }

        // 密码强度校验：包含数字、小写字母、大写字母、特殊符号中的三个
        int complexity = 0;
        if (password.matches(".*\\d.*")) { // 包含数字
            complexity++;
        }
        if (password.matches(".*[a-z].*")) { // 包含小写字母
            complexity++;
        }
        if (password.matches(".*[A-Z].*")) { // 包含大写字母
            complexity++;
        }
        if (password.matches(".*[^a-zA-Z0-9].*")) { // 包含特殊符号
            complexity++;
        }

        if (complexity < 3) {
            return Pair.of(1, "密码强度不足，至少包含数字、小写字母、大写字母、特殊符号中的三种");
        }
        return Pair.of(0, "success");
    }

    public Pair<Integer, String> nicknameValid(String nickname){
        if (!StringUtils.hasLength(nickname)) {
            return Pair.of(1, "昵称不能为空");
        }
        if (nickname.length() < 6 || nickname.length() > 20) {
            return Pair.of(1, "昵称长度必须在6-20个字符之间");
        }
        return Pair.of(0, "success");
    }

    public Pair<Integer, String> register(String username, String password, String nickname){
        // 验证用户名
        var usernameResponse = usernameValid(username);
        if (usernameResponse.getLeft() != 0) {
            return usernameResponse;
        }

        // 验证密码
        var passwordResponse = passwordValid(password);
        if (passwordResponse.getLeft() != 0) {
            return passwordResponse;
        }

        // 验证昵称
        var nicknameResponse = nicknameValid(nickname);
        if (nicknameResponse.getLeft() != 0) {
            return nicknameResponse;
        }

        return Pair.of(0, "success");
    }

    public Pair<Integer, String> login(String username, String password){
        // 验证用户名
        var usernameResponse = usernameValid(username);
        if (usernameResponse.getLeft() != 0) {
            return usernameResponse;
        }

        // 验证密码
        var passwordResponse = passwordValid(password);
        if (passwordResponse.getLeft() != 0) {
            return passwordResponse;
        }

        return Pair.of(0, "success");
    }

    public Pair<Integer, String> updateBasic(String username, String nickname){
        // 验证用户名
        var usernameResponse = usernameValid(username);
        if (usernameResponse.getLeft() != 0) {
            return usernameResponse;
        }

        // 验证密码
        var passwordResponse = nicknameValid(nickname);
        if (passwordResponse.getLeft() != 0) {
            return passwordResponse;
        }

        return Pair.of(0, "success");
    }
}
