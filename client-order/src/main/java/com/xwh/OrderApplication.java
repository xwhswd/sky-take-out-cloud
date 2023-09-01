package com.xwh;

import com.xwh.clients.AddressClient;
import com.xwh.clients.ShoppingCartClient;
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
@EnableFeignClients(clients = {AddressClient.class, ShoppingCartClient.class, UserClient.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
