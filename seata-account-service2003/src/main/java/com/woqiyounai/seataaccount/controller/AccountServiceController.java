package com.woqiyounai.seataaccount.controller;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.seataaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountServiceController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/api/account/decrease")
    public CommonResponse decrease(@RequestParam("userId")Long userId, @RequestParam("money")BigDecimal money){
        accountService.decrease(userId, money);
        return new CommonResponse(200,"扣钱成功");
    }
}
