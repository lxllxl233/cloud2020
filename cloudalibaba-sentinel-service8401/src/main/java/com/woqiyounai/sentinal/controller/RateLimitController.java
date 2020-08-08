package com.woqiyounai.sentinal.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.sentinal.handler.MyHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/api/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResponse byResource(){
        return new CommonResponse(200,"按资源名称限流测试ok",new Payment(2020L,"serial001"));
    }

    @GetMapping("/api/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResponse byUrl(){
        return new CommonResponse(200,"按URL限流测试ok",new Payment(2020L,"serial001"));
    }

    public CommonResponse handleException(BlockException e){
        return new CommonResponse(444,e.getClass().getCanonicalName()+"\t服务不可用");
    }

    @GetMapping("/api/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = MyHandler.class,blockHandler = "handlerException2")
    public CommonResponse customerBlockHandler(){
        return new CommonResponse(200,"按U客户自定义限流测试ok",new Payment(2020L,"serial001"));
    }
}
