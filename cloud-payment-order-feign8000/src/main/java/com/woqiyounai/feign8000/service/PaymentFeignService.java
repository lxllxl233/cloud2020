package com.woqiyounai.feign8000.service;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    @GetMapping("/api/payment/getPaymentById")
    CommonResponse<Payment> getPaymentById(@RequestParam(value = "id",defaultValue = "0") Long id);
}
