package com.woqiyounai.seataaccount.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.woqiyounai.seataaccount.dao")
public class MybatisConfig {
}