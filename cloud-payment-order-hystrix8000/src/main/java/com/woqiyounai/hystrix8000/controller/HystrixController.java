package com.woqiyounai.hystrix8000.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.woqiyounai.hystrix8000.service.HystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@DefaultProperties(
//        defaultFallback = "globalMethod"
//)
@RestController
public class HystrixController {

    @Autowired
    HystrixService hystrixService;

    @HystrixCommand
    @GetMapping("/consumer/payment/ok")
    public String payment_OK(Integer id){
//        int i = 1/0;
        return hystrixService.payment_OK(id);
    }


    //等待时间 3 s
//    @HystrixCommand(fallbackMethod = "paymentTimeoutMethod",
//        commandProperties = {
//            //hystrix.command.default.   execution.isolation.thread.timeoutInMilliseconds
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "4000")
//        }
//    )
    @GetMapping("/consumer/payment/timeout")
    public String payment_NOT_OK(Integer id){
        return hystrixService.payment_NOT_OK(id);
    }

    public String paymentTimeoutMethod(Integer id){
        return "(兜底-客户端)线程池 "+Thread.currentThread().getName()+" paymentInfo_NOT_OK id="+id;
    }

    public String globalMethod(){
        return "(兜底-客户端)线程池 "+Thread.currentThread().getName()+" 服务降级全局通用方法";
    }
}
