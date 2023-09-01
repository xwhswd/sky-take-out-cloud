package com.xwh.service.impl;

import com.xwh.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class ShopServiceImpl implements ShopService {
    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public int getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        return status;
    }

    @Override
    public boolean setStatus(int status) {
        redisTemplate.opsForValue().set(KEY,status);
        return true;
    }
}
