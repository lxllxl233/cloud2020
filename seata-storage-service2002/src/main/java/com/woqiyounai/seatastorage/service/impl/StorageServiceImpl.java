package com.woqiyounai.seatastorage.service.impl;

import com.woqiyounai.seatastorage.dao.StorageDao;
import com.woqiyounai.seatastorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        storageDao.decrease(productId,count);
    }
}
