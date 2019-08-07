package com.comic.blank.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author wangchen870
 * createAt 2019/5/28
 * updateAt 2019/5/28
 */
public class RedisTemp {

    @Autowired
    private RedisTemplate redisTemplate;

    public void get() throws InterruptedException {
//        从Redis读取数据，如果没有读取到，则缓存没有添加或者失效防止缓存击穿
        Object o = redisTemplate.opsForValue().get("123");
        if(o == null){
            System.out.println("尝试获取锁对象");
            Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", 321);
            if(lock){
                String name = "黄康";
//                拿到锁，给Redis添加缓存
                redisTemplate.opsForValue().set("123",name);
                //给锁设定超时时间防止死锁
                redisTemplate.opsForValue().set("lock",name , 3L, TimeUnit.SECONDS);

            }else {
//                没有拿到锁则递归调用当前方法，此时先睡眠100毫秒防止数据未添加读取
                Thread.sleep(100);
            }
            System.out.println(lock ? "成功获取到锁" : "获取锁失败");
//            调用自生方法，此时数据已经添加到Redis中直接重新读取返回
            get();
        }else{
//            已经获取到数据直接返回查询到的数据
            System.out.println(o);
        }
    }

}
 
