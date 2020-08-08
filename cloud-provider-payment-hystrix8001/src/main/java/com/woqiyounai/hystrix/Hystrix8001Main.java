package com.woqiyounai.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class Hystrix8001Main {
    public static void main(String[] args) {
        SpringApplication.run(Hystrix8001Main.class,args);
    }
}
