package com.woqiyounai.hystrix.service;


import com.woqiyounai.common.entities.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(Long id);

    String paymentInfo_OK(Integer id);
    String paymentInfo_NOT_OK(Integer id);
    String paymentCircuitBreaker(Integer id);
}
