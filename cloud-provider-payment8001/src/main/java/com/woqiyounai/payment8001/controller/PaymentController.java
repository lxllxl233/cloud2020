package com.woqiyounai.payment8001.controller;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;
import com.woqiyounai.payment8001.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@CrossOrigin
@RestController
public class PaymentController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/api/payment/get/{id}")
    public CommonResponse<Payment> getPaymentById1(@PathVariable("id") Long id){
        //timeOut();
        Payment paymentById = paymentService.getPaymentById(id);
        if (null != paymentById){
            return new CommonResponse<>(200,"获取成功"+serverPort,paymentById);
        }else {
            return new CommonResponse<>(500,"获取失败"+serverPort,null);
        }
    }

    @GetMapping("/api/payment/lb")
    public String getLb(){
        return serverPort;
    }

    @GetMapping("/api/payment/zikpin")
    public String getZikpin(){
        return "hello,i am zikpin";
    }


    @PostMapping("/api/payment/create")
    public CommonResponse<Integer> creste(@RequestBody Payment payment){
        int i = paymentService.create(payment);
        log.info("****插入****"+i);
        if (i > 0){
            return new CommonResponse<>(200,"创建成功"+serverPort,i);
        }else {
            return new CommonResponse<>(500,"创建失败"+serverPort,i);
        }
    }

    @GetMapping("/api/payment/getPaymentById")
    public CommonResponse<Payment> getPaymentById(Long id){
        //timeOut();
        Payment paymentById = paymentService.getPaymentById(id);
        if (null != paymentById){
            return new CommonResponse<>(200,"获取成功"+serverPort,paymentById);
        }else {
            return new CommonResponse<>(500,"获取失败"+serverPort,null);
        }
    }

    @GetMapping("/api/payment/discovery")
    public Object discoverty(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println("==="+service+"===");
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getUri());
            }
        }
        return discoveryClient;
    }

    public void timeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
