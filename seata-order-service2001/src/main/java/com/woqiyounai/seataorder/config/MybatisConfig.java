package com.woqiyounai.seataorder.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.woqiyounai.seataorder.dao")
public class MybatisConfig {
}