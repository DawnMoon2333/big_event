package com.dawnmoon.big_event.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
// 自动为类生成没有参数和有按顺序声明的变量的构造方法
@Data
public class Response<T> {
    private Integer code;
    private String message;
    private T data;


    public static Response<?> success(){
        return new Response<>(0, "操作成功", null);
    }

    public static Response<?> success(String message){
        return new Response<>(0, message, null);
    }

    public static <T> Response<T> success(String message, T data){
        return new Response<>(0, message, data);
    }

    public static Response<?> error(Integer code, String message){
        return new Response<>(code, message, null);
    }

}

