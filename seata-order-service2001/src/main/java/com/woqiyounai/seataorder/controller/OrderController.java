package com.woqiyounai.seataorder.controller;

import com.woqiyounai.common.bean.Order;
import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.seataorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/order/create")
    public CommonResponse create(Order order){
        orderService.create(order);
        return new CommonResponse(200,"订单创建成功");
    }
}
