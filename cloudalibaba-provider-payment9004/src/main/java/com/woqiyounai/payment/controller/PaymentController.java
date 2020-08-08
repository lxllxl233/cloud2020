package com.woqiyounai.payment.controller;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;
    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"p1"));
        hashMap.put(2L,new Payment(2L,"p2"));
        hashMap.put(3L,new Payment(3L,"p3"));
    }

    @GetMapping("/api/payment/{id}")
    public CommonResponse<Payment> paymentSQL(@PathVariable("id")Long id){
        Payment payment = hashMap.get(id);
        return new CommonResponse<>(200,"serverport-"+serverPort,payment);
    }
}
