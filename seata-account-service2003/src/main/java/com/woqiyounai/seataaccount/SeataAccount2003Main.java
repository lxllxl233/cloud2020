package com.woqiyounai.seataaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SeataAccount2003Main {

    public static void main(String[] args) {
        SpringApplication.run(SeataAccount2003Main.class,args);
    }
}
