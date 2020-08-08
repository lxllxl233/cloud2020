package com.woqiyounai.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConsumerNacos8000Main {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerNacos8000Main.class,args);
    }
}
