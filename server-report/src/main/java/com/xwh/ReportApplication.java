package com.xwh;

import com.xwh.clients.CategoryClient;
import com.xwh.clients.OrderClient;
import com.xwh.clients.UserClient;
import com.xwh.clients.WorkspaceClient;
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
@EnableFeignClients(clients = {CategoryClient.class, UserClient.class, OrderClient.class, WorkspaceClient.class},defaultConfiguration = DefaultFeignConfiguration.class)
public class ReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class,args);
    }
}
