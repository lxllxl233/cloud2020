package com.woqiyounai.seatastorage.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.woqiyounai.seatastorage.dao")
public class MybatisConfig {
}