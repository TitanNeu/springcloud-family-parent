package com.daddy.service.concurrent;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @ClassName MultiThreadWriteBitmap
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/7 14:19
 * @Version 1.0
 **/
public class MultiThreadWriteBitmap implements Runnable{
    private int startIndex;
    private int endIndex;
    private StringRedisTemplate redisTemplate;

    public MultiThreadWriteBitmap(int startIndex, int endIndex, StringRedisTemplate redisTemplate) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for(int i = startIndex; i <= endIndex; i++) {
            redisTemplate.opsForValue().setBit("test_bitmap", i, i%2==0?false:true);
        }
        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName()+"耗时(ms): "+(end-start));

    }
}
