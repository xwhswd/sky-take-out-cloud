package com.xwh.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: e-commerce
 * @description: knife4j配置类
 * @author: JiaChaoYang
 * @date: 2022-09-24 21:03
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfiguration {

    /*需要在配置文件里配置这三个配置*/
    /*配置开启禁用swagger*/
    @Value("${swagger.enabled}")
    private boolean enabled;

    /*配置模块名*/
    @Value("${swagger.groupName}")
    private String groupName;

    /*配置需要扫描的包*/
    @Value("${swagger.basePackage}")
    private String basePackage;

    /**
     * @Description: Swagger 实例 Bean是Docket，所以通过配置Docket实例来配置Swagger
     * @return: springfox.documentation.spring.web.plugins.Docket
     * @Author: JiaChaoYang
     * @Date: 2022-09-24 - 11:36
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)//文档类型，我这里使用的是swagger3
                //是否启用swagger
                .enable(enabled)
                //包名，模块名
                .groupName(groupName)
                //删除swagger中的操作的响应体
                .useDefaultResponseMessages(false)
                //展示在Swagger页面上的自定义工程描述信息
                .apiInfo(apiInfo())
                //选择展示哪些接口
                .select()
                //配置需要扫描的路径（controller）
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                //给所有文档都生成文档路径
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @Description: Swagger的描述信息
     * @return: springfox.documentation.service.ApiInfo
     * @Author: JiaChaoYang
     * @Date: 2022-09-24 - 11:33
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //描述信息
                .description("测试Knife4j")
                //可以通过swagger联系一个人，即联系方式
                .contact(new Contact("JiaChaoYang", "https://127.0.0.1:9001/doc.html", "j15030047216@163.com"))
                //版本
                .version("v1.0")
                //标题
                .title("测试文档")
                .build();
    }
}
