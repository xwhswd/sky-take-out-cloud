package com.xwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AddressBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class,args);
    }
}
