package com.dawnmoon.big_event.service;

import com.dawnmoon.big_event.pojo.User;

public interface UserService {
    public User register(String name, String password, String nickname);

    public User findByUsername(String username);

    void updateBasic(User user);

    void updatePic(String picUrl);

    void updatePwd(String newPwd);
}
