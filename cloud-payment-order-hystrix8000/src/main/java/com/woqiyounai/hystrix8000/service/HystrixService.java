package com.woqiyounai.hystrix8000.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE",fallback = HystrixServiceImpl.class)
public interface HystrixService {

    @GetMapping("/payment/hystrix/ok")
    String payment_OK(@RequestParam(value = "id") Integer id);

    @GetMapping("/payment/hystrix/timeout")
    String payment_NOT_OK(@RequestParam(value = "id") Integer id);
}
