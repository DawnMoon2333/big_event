package com.dawnmoon.big_event.mapper;

import com.dawnmoon.big_event.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("insert into big_event.user(username, password, nickname, create_time) " +
            "values(#{username},#{encryptedPassword},#{nickname}, now())")
    void register(String username, String encryptedPassword, String nickname);

    @Select("select * from big_event.user where username=#{username}")
    User findByName(String username);

    @Update("update big_event.user set nickname=#{nickname}, username=#{username} where id=#{id}")
    void updateBasic(User user);

    @Update("update big_event.user set user_pic_url=#{picUrl} where id=#{id}")
    void updatePic(Integer id, String picUrl);

    @Update("update big_event.user set password=#{encryptedPwd} where id=#{id}")
    void updatePwd(Integer id, String encryptedPwd);
}
