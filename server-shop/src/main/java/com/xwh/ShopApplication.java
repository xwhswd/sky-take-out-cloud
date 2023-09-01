package com.xwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/30
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class,args);
    }

}
