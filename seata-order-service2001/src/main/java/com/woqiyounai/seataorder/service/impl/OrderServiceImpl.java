package com.woqiyounai.seataorder.service.impl;

import com.woqiyounai.common.bean.Order;
import com.woqiyounai.seataorder.dao.OrderDao;
import com.woqiyounai.seataorder.service.AccountService;
import com.woqiyounai.seataorder.service.OrderService;
import com.woqiyounai.seataorder.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageService storageService;
    @Autowired
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("新建订单");
        orderDao.create(order);
        log.info("正在扣减库存");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("正在扣减余额");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("扣减完成");
        log.info("修改订单状态 0-->1 ");
        orderDao.update(order.getUserId(),0);
        log.info("下订单结束了");
    }
}
