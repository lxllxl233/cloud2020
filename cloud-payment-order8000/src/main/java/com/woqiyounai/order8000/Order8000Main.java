package com.woqiyounai.order8000;

import com.woqiyounai.myrule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MyRule.class)
@EnableEurekaClient
@SpringBootApplication
public class Order8000Main {

    public static void main(String[] args) {
        SpringApplication.run(Order8000Main.class,args);
    }

}
