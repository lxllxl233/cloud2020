package com.woqiyounai.consumer.service;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("nacos-payment-provider")
public interface PaymentFeignService {
    @GetMapping("/api/payment/{id}")
    CommonResponse<Payment> getPaymentById(@PathVariable("id") Long id);
}
