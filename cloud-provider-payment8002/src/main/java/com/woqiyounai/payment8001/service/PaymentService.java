package com.woqiyounai.payment8001.service;


import com.woqiyounai.common.entities.Payment;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(Long id);
}
