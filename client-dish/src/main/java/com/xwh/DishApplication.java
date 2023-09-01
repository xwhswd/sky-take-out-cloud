package com.xwh;

import com.xwh.clients.CategoryClient;
import com.xwh.clients.DishClient;
import com.xwh.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/30
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableFeignClients(clients = CategoryClient.class,defaultConfiguration = DefaultFeignConfiguration.class)
public class DishApplication {
    public static void main(String[] args) {
        SpringApplication.run(DishApplication.class,args);
    }
}
