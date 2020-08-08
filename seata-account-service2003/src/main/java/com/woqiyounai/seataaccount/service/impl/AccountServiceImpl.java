package com.woqiyounai.seataaccount.service.impl;

import com.woqiyounai.seataaccount.dao.AccountDao;
import com.woqiyounai.seataaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    @Override
    public void decrease(Long userId, BigDecimal money) {
        //模拟出错 : 超时异常，希望全局事务回滚
        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accountDao.decrease(userId,money);
    }
}
