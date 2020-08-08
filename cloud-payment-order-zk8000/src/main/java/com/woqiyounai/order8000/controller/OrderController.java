package com.woqiyounai.order8000.controller;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class OrderController {

    //CLOUD-PAYMENT-SERVICE
    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResponse<Integer> create(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/api/payment/create",payment,CommonResponse.class);
    }

    @GetMapping("/consumer/payment/getPaymentById")
    public CommonResponse<Payment> getPaymentById(Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/api/payment/getPaymentById?id="+id,CommonResponse.class);
    }

}
