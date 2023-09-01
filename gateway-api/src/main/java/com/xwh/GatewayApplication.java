package com.xwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
