package com.woqiyounai.hystrix8000;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class Hystrix8000Main {
    public static void main(String[] args) {
        SpringApplication.run(Hystrix8000Main.class,args);
    }
}
