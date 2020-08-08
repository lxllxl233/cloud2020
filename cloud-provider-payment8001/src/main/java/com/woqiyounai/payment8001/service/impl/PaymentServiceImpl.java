package com.woqiyounai.payment8001.service.impl;

import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.payment8001.dao.PaymentDao;
import com.woqiyounai.payment8001.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
