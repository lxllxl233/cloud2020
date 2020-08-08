package com.woqiyounai.seatastorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SeataStorage2002Main {

    public static void main(String[] args) {
        SpringApplication.run(SeataStorage2002Main.class,args);
    }
}
