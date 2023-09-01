package com.xwh;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 * description:暂废,放了点垃圾代码
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class,args);

    }

}
