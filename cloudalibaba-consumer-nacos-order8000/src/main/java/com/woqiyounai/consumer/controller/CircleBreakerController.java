package com.woqiyounai.consumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.consumer.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircleBreakerController {

    @Value("${service-url}")
    private String serviceUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PaymentFeignService paymentFeignService;

    @SentinelResource(
            value = "fallback",
            blockHandler = "blockHandlerMethod",
            fallback = "handlerFallbackMethod",
            //忽略某个异常
            exceptionsToIgnore = IllegalArgumentException.class
    )
    @GetMapping("/api/fallback/{id}")
    public CommonResponse<Payment> fallback(@PathVariable("id") Long id) {
        CommonResponse<Payment> result = paymentFeignService.getPaymentById(id);
        if (id == 4){
            throw new IllegalArgumentException("非法参数异常");
        }else if (result.getData() == null){
            throw new NullPointerException("该 id 没有对应的记录");
        }
        return result;
    }

    public CommonResponse<Payment> handlerFallbackMethod(@PathVariable("id")Long id,Throwable e){
        return new CommonResponse<>(444,"兜底异常:"+e.getMessage(),null);
    }
    public CommonResponse<Payment> blockHandlerMethod(@PathVariable("id")Long id,Throwable e){
        return new CommonResponse<>(445,"sentinel限流兜底异常:"+e.getMessage(),null);
    }

}
