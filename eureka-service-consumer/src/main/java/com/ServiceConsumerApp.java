package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName com.ServiceConsumerApp
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 13:50
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
public class ServiceConsumerApp {
    public static void main(String[] args) {
        System.out.println("service consumer start");
        SpringApplication.run(ServiceConsumerApp.class, args);
    }
}
