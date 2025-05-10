package com.dawnmoon.big_event.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDateTime;

// 来自lombok依赖，用于在编译时为实体类自动生成setter getter tostring等方法
@Data
public class User {
    private Integer id;
    private String username;
    @JsonIgnore // 返回User对象时不返回password
    private String password;
    private String nickname;
    private String userPicUrl;
    private LocalDateTime createTime;
}
