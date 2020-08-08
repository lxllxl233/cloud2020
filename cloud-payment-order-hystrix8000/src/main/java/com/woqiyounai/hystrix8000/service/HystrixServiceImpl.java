package com.woqiyounai.hystrix8000.service;

import org.springframework.stereotype.Component;

@Component
public class HystrixServiceImpl implements HystrixService{
    @Override
    public String payment_OK(Integer id) {
        return "异常 - OK方法调用失败";
    }

    @Override
    public String payment_NOT_OK(Integer id) {
        return "异常 - not ok方法调用失败";
    }
}
