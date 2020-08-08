package com.woqiyounai.feign8000.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContext {

    @Bean
    @LoadBalanced //赋予负载均衡的能力
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
