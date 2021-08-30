package indi.nyp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class BigDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(BigDataApplication.class, args);
    }
}
