package com.woqiyounai.sentinal.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class FlowLimitController {

    @GetMapping("/api/testA")
    public String testA(){
        return "------testA";
    }

    @GetMapping("/api/testB")
    public String testB(){
        log.info(String.valueOf(TimeUnit.values()));
        return "------testB";
    }

    @GetMapping("/api/testD")
    public String testD(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(String.valueOf(TimeUnit.values())+" : test-D");
        return "------testD";
    }

    @GetMapping("/api/testE")
    public String testE(){
        log.info(String.valueOf(TimeUnit.values())+" : test-异常比例");
        int a = 10/0;
        return "------test异常比例";
    }

    @GetMapping("/api/testF")
    public String testF(){
        log.info(String.valueOf(TimeUnit.values())+" : test-异常数");
        int a = 10/0;
        return "------test异常数";
    }

    @GetMapping("/api/testHotKey")
    @SentinelResource(
            value = "testHotKey",
            blockHandler = "dealTestHotKey"
    )
    public String testHotKey(
            @RequestParam(value = "p1",required = false)String p1,
            @RequestParam(value = "p2",required = false)String p2){
        return "HotKey 测试";
    }

    //运行时兜底
    public String dealTestHotKey(String p1, String p2, BlockException exception){
        return "deal hot key.";
    }
}
