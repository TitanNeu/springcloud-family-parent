package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName com.EurekaServerApp
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 13:48
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {
    public static void main(String[] args) {
        System.out.println("eureka server start");
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
