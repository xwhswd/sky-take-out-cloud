package com.xwh;

import com.xwh.clients.DishClient;
import com.xwh.clients.OrderClient;
import com.xwh.clients.SetmealClient;
import com.xwh.clients.UserClient;
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
@EnableFeignClients(clients = {OrderClient.class, UserClient.class, SetmealClient.class, DishClient.class})
public class WorkSpaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkSpaceApplication.class,args);
    }
}
