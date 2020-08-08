package com.woqiyounai.nacospro1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Nacos9001Main {

    public static void main(String[] args) {
        SpringApplication.run(Nacos9001Main.class,args);
    }
}
