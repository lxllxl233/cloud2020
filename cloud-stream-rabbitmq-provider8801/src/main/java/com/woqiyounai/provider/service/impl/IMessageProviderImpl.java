package com.woqiyounai.provider.service.impl;

import cn.hutool.core.lang.UUID;
import com.woqiyounai.provider.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

//定义消息推送管道
@Slf4j
@EnableBinding(Source.class)
public class IMessageProviderImpl implements IMessageProvider {

    //消息发送管道
    @Autowired
    private MessageChannel output;

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        log.info("*** serial ***"+serial);
        return null;
    }
}
