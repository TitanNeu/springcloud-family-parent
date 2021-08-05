package com.daddy.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 14:07
 * @Version 1.0
 **/
@Configuration
public class WebConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
