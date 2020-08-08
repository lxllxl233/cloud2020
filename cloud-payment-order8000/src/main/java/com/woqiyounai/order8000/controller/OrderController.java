package com.woqiyounai.order8000.controller;

import com.netflix.discovery.converters.Auto;
import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.order8000.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class OrderController {

    //注入自己的算法
    @Autowired
    private LoadBalancer loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;

    //CLOUD-PAYMENT-SERVICE
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResponse<Integer> create(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/api/payment/create",payment,CommonResponse.class);
    }

    @GetMapping("/consumer/payment/getPaymentById")
    public CommonResponse<Payment> getPaymentById(Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/api/payment/getPaymentById?id="+id,CommonResponse.class);
    }

    @GetMapping("/consumer/payment/zikpin")
    public String getZikpin(){
        return restTemplate.getForObject(PAYMENT_URL+"/api/payment/zikpin",String.class);
    }

    @GetMapping("/consumer/payment/getPaymentByIdEntity")
    public ResponseEntity<CommonResponse> getPaymentByIdEntity(Long id){
        ResponseEntity<CommonResponse> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/api/payment/getPaymentById?id=" + id, CommonResponse.class);
        return forEntity;
    }

    @GetMapping("/api/test/loadbalance")
    public CommonResponse<Payment> getPaymentLB(Integer id){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (null == instances || instances.size() == 0){
            return null;
        }else {
            //获取服务实例
            ServiceInstance service = loadBalancer.instances(instances);
            URI uri = service.getUri();
            return restTemplate.getForObject(uri+"/api/payment/getPaymentById?id="+id,CommonResponse.class);
        }
    }

}
