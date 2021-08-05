package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName RedisRelativeApp
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/1 10:35
 * @Version 1.0
 **/
@SpringBootApplication
public class RedisRelativeApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisRelativeApp.class, args);
    }

}
