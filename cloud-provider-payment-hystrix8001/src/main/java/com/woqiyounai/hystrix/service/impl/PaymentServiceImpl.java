package com.woqiyounai.hystrix.service.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.hystrix.dao.PaymentDao;
import com.woqiyounai.hystrix.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    //测试服务熔断的方法
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",
    commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    @Override
    public String paymentCircuitBreaker(Integer id){
        if (id < 0){
            throw new RuntimeException("id 不能为负数");
        }
        String UUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"  调用成功，流水号:"+UUID;
    }
    public String paymentCircuitBreaker_fallback(Integer id){
        return "请求失败";
    }

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    //正确方法
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池 "+Thread.currentThread().getName()+" paymentInfo_OK id="+id;
    }



    //异常方法：超时
    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",
                    commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
                    }
                    )
    public String paymentInfo_NOT_OK(Integer id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池 "+Thread.currentThread().getName()+" paymentInfo_NOT_OK id="+id;
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "(兜底)线程池 "+Thread.currentThread().getName()+" paymentInfo_NOT_OK id="+id;
    }
}
