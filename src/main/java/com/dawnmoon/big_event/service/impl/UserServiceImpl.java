package com.dawnmoon.big_event.service.impl;

import com.dawnmoon.big_event.mapper.UserMapper;
import com.dawnmoon.big_event.pojo.User;
import com.dawnmoon.big_event.service.UserService;
import com.dawnmoon.big_event.utils.PasswordUtil;
import com.dawnmoon.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordUtil passwordUtil;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordUtil passwordUtil) {
        this.userMapper = userMapper;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public User register(String username, String password, String nickname) {
        String encryptedPassword = passwordUtil.SHA256Encrypt(password);
        userMapper.register(username, encryptedPassword, nickname);
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public void updateBasic(User user) {

        userMapper.updateBasic(user);
    }

    @Override
    public void updatePic(String picUrl){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePic(id, picUrl);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(id, passwordUtil.SHA256Encrypt(newPwd));
    }
}
