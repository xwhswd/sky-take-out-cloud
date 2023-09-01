package com.xwh.task.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/24
 */
@Configuration
public class MinioConfig {
    @Value("${minio.ip}")
    private String ip;
    @Value("${minio.port}")
    private int port;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(ip, port, false) //https or not
                .credentials(accessKey, secretKey)//加密认证
                .build();//创建客户端
    }

}
