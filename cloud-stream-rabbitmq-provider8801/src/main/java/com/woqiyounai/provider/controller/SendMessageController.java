package com.woqiyounai.provider.controller;

import com.woqiyounai.provider.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

    @Autowired
    private IMessageProvider messageProvider;

    @GetMapping("/api/message/sendMessage")
    public String sendMessage(){
        return messageProvider.send();
    }
}
