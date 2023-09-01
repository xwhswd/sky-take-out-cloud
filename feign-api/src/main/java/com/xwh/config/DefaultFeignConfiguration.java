package com.xwh.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}
