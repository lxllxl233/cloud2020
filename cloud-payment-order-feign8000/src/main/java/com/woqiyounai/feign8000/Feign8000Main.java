package com.woqiyounai.feign8000;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Feign8000Main {
    public static void main(String[] args) {
        SpringApplication.run(Feign8000Main.class,args);
    }
}
