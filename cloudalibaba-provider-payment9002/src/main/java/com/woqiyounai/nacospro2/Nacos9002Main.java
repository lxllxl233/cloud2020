package com.woqiyounai.nacospro2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Nacos9002Main {

    public static void main(String[] args) {
        SpringApplication.run(Nacos9002Main.class,args);
    }
}
