package com.woqiyounai.seataorder.dao;

import com.woqiyounai.common.bean.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {
    void create(Order order);
    void update(Long userId,Integer status);
}
