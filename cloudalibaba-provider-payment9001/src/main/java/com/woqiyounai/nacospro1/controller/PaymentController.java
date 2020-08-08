package com.woqiyounai.nacospro1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/api/payment/port/{id}")
    public String getServerPort(@PathVariable("id") String id){
        return "Nacos - port : "+serverPort;
    }
}
