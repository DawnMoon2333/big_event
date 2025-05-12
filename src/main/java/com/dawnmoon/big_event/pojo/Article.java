package com.dawnmoon.big_event.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;
    @Min(1) // 从1开始
    private Integer categoryId;
    @Min(1) // 从1开始
    private Integer createUser;
    @Pattern(regexp = "^.{1,64}$") // 任意1-64个字符
    private String title;
    private String content;
    private String coverPicUrl;
    @Min(0)
    @Max(1) // 只能取0或1
    private Integer state;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
