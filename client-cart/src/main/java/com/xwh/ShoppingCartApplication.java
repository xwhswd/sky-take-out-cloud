package com.xwh;

import com.xwh.clients.CategoryClient;
import com.xwh.clients.DishClient;
import com.xwh.clients.SetmealClient;
import com.xwh.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableFeignClients(clients = {CategoryClient.class, DishClient.class, SetmealClient.class},defaultConfiguration = DefaultFeignConfiguration.class)
public class ShoppingCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartApplication.class,args);
    }
}
