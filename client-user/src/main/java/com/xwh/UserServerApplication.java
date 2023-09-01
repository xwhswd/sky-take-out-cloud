package com.xwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCaching
public class UserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class,args);
    }
}
