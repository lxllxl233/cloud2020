package com.woqiyounai.feign8000.controller;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.feign8000.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;


    @GetMapping("/consumer/payment/getPaymentById")
    public CommonResponse<Payment> getPaymentById(Long id){
        return paymentFeignService.getPaymentById(id);
    }
}
