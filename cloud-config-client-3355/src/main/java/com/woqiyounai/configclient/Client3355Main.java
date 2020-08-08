package com.woqiyounai.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Client3355Main {
    public static void main(String[] args) {
        SpringApplication.run(Client3355Main.class,args);
    }
}
