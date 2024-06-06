package com.itheima.reggie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class redistest {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void String(){
        //向redis中保存数据
        redisTemplate.opsForValue().set("alv","123456");
        //从redis中获取数据
        String alv = (String)redisTemplate.opsForValue().get("alv");
        System.out.println(alv);
    }
    @Test
    public void hash(){
        redisTemplate.opsForHash().put("user","name","张三");
        redisTemplate.opsForHash().put("user","age","20");
        System.out.println(redisTemplate.opsForHash().get("user","name"));
        System.out.println(redisTemplate.opsForHash().get("user","age"));
    }
}
