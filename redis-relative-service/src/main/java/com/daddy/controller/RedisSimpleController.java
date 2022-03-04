package com.daddy.controller;

import com.daddy.concurrent.utils.ThreadPoolUtils;
import com.daddy.service.concurrent.MultiThreadWriteBitmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisSimpleController
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/6 17:31
 * @Version 1.0
 **/
@RestController
public class RedisSimpleController {
    private static final Logger logger = LoggerFactory.getLogger(RedisSimpleController.class);
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping(value = "/redis/getRedisDataByKey")
    public void test1(@RequestBody Map<String, String> redisKeyMap) {

        for (Map.Entry<String, String> entry : redisKeyMap.entrySet()) {
            if (entry.getKey() == null) {
                logger.info("hash_key为空");
                continue;
            }
            Object o = redisTemplate.opsForValue().get(entry.getKey());
            if (o == null) {
                return;
            }
            System.out.println(o.toString());
        }

    }

    @PostMapping(value = "/redis/multiThreadBitmap")

    public void test2(@RequestBody Map<String, Integer> configMap) {
        int corePoolSize = configMap.getOrDefault("corePoolSize", 8);
        int maxPoolSize = configMap.getOrDefault("maxPoolSize", 12);
        int keepAliveTime = configMap.getOrDefault("keepAliveTime", 30);
        int queueCapacity = configMap.getOrDefault("queueCapacity", 2);
        int delta = configMap.getOrDefault("delta", 1249);
        int maxIndex = configMap.getOrDefault("maxIndex", 10000);

        ThreadPoolExecutor executorService = ThreadPoolUtils.getThreadPool(corePoolSize, maxPoolSize, keepAliveTime, queueCapacity);
//        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < maxIndex; i += (delta + 1)) {
//            System.out.println("开始"+i+"   结束"+(i+delta));
            executorService.submit(new MultiThreadWriteBitmap(i, i + delta, stringRedisTemplate));
        }
    }

    @PostMapping(value = "/redis/singleThreadBitmap")
    public void test3(@RequestBody Map<String, Integer> configMap) {
        int maxIndex = configMap.getOrDefault("maxIndex", 10000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < maxIndex; i++) {
            redisTemplate.opsForValue().setBit("test_bitmap", i, i % 2 == 0 ? false : true);
        }

        long end = System.currentTimeMillis();

        System.out.println("单线程" + (end - start) + "ms");


    }
    private static final String lock = "lock";
    private static final String num = "num";
    private static final String delLockLua = "local lock = KEYS[1]\n" +
            "local lockVal = ARGV[1]\n" +
            "if redis.call('get',lock) == lockVal then\n" +
            "    redis.call('del',lock)\n" +
            "else\n" +
            "    return 0\n" +
            "end";
    //测试lua脚本和分布式锁
    @GetMapping(value = "/redis/testlua")
    public void testLua() {
        String uuid = UUID.randomUUID().toString();
        //尝试设置锁
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lock, uuid, 3, TimeUnit.SECONDS);
        DefaultRedisScript<Long> delLockScript = new DefaultRedisScript<>();
        delLockScript.setScriptText(delLockLua);
        delLockScript.setResultType(Long.class);

        if(Boolean.TRUE.equals(flag)) { //获得了锁
            redisTemplate.opsForValue().increment(num);
            redisTemplate.execute(delLockScript, Collections.singletonList(lock), uuid);
        }else{
            try {
                Thread.sleep(100);
                testLua();
            } catch (InterruptedException e) {
                logger.error("sleep error",e);
            }
        }

    }
}
