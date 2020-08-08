package com.woqiyounai.seataorder.service;

import com.woqiyounai.common.entities.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("seata-storage-service")
public interface StorageService {

    @PostMapping(value = "/api/storage/decrease")
    CommonResponse decrease(@RequestParam("productId")Long productId,@RequestParam("count")Integer count);
}
