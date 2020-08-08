package com.woqiyounai.eureka7001;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Eureka7001Main {
    public static void main(String[] args) {
        SpringApplication.run(Eureka7001Main.class,args);
    }
}
