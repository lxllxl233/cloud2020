package com.woqiyounai.order8000;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Order8000ConsulMain {

    public static void main(String[] args) {
        SpringApplication.run(Order8000ConsulMain.class,args);
    }

}
