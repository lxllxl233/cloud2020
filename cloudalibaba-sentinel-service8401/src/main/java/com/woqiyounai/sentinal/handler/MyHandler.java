package com.woqiyounai.sentinal.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.common.entities.Payment;

public class MyHandler {

    public static CommonResponse handlerException1(BlockException exception){
        return new CommonResponse(000,"按客户自定义的处理 - 1",new Payment(2020L,"serial001"));
    }

    public static CommonResponse handlerException2(BlockException exception){
        return new CommonResponse(000,"按客户自定义的处理 - 2",new Payment(2020L,"serial001"));
    }
}
