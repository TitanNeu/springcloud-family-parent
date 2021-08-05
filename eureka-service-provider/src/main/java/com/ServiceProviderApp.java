package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName com.ServiceProviderApp
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 13:51
 * @Version 1.0
 **/
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderApp {
    public static void main(String[] args) {
        System.out.println("service provider start");
        SpringApplication.run(ServiceProviderApp.class, args);
    }
}
