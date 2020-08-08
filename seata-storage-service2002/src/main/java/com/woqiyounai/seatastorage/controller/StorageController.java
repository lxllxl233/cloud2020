package com.woqiyounai.seatastorage.controller;

import com.woqiyounai.common.entities.CommonResponse;
import com.woqiyounai.seatastorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/api/storage/decrease")
    public CommonResponse decrease(Long productId,Integer count){
        storageService.decrease(productId, count);
        return new CommonResponse(100,"扣减库存成功");
    }
}
