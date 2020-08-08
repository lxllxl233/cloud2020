package com.woqiyounai.seataorder.service;

import com.woqiyounai.common.entities.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("seata-account-service")
public interface AccountService {

    @PostMapping(value = "/api/account/decrease")
    CommonResponse decrease(@RequestParam("userId")Long userId, @RequestParam("money") BigDecimal money);
}
