package com.dawnmoon.big_event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

// 在测试类加入这个注解，则会在单元测试方法执行之前先初始化Spring容器
@SpringBootTest
public class RedisTest {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisTest(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Test
    public void testSet(){
        // 存储一个键值对
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("key1", "value1");
        operations.set("key2", "value2", 15, TimeUnit.SECONDS); // 15秒后过期

    }

    @Test
    public void testGet(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("key1"));

    }
}
