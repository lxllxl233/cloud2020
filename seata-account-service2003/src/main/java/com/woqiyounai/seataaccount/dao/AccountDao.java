package com.woqiyounai.seataaccount.dao;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface AccountDao {

    void decrease(Long userId, BigDecimal money);
}
