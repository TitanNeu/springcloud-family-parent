package com.daddy.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName EurekaConfigs
 * @Description TODO
 * @Author niuyp
 * @Date 2021/6/30 15:20
 * @Version 1.0
 **/


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 支持httpBasic
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
