package com.woqiyounai.nacoscon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Value("${service-url.nacos-user-service}")
    private String providerName;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api/consumer/nacos/{id}")
    public String getPayment(@PathVariable("id") String id){
        return restTemplate.getForObject(providerName+"/api/payment/port/"+id,String.class);
    }
}
