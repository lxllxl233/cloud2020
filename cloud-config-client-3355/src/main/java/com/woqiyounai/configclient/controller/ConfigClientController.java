package com.woqiyounai.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigClientController {

    @Value("${name}")
    private String branch;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/api/config/serverPort")
    public String getServerPort(){
        return serverPort;
    }

    @GetMapping("/api/config/branch")
    public String getBranch(){
        return branch;
    }

    @GetMapping("/api/config/test")
    public String test(){
        return "接口正常";
    }
}

