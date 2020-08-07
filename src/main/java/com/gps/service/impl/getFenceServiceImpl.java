package com.gps.service.impl;

import com.gps.mappers.gpsFence;
import com.gps.service.getFenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class getFenceServiceImpl implements getFenceService {
    @Autowired
    gpsFence  gpsFence;
    @Override
    public Integer addFence(String value) {
        return gpsFence.saveFence(value);
    }

    @Override
    public Integer delFence() {
        return gpsFence.delFence();
    }
}
