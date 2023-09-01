package com.xwh;

import com.xwh.config.AliOssProperties;
import com.xwh.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/30
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Slf4j
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传工具类对象：{}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
