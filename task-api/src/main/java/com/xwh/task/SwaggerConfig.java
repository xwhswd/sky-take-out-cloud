package com.xwh.task;

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
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("这是knife4j API ")
                        .description("# 这里记录服务端所有的接口的入参，出参等等信息")
                        .termsOfServiceUrl("http://yaomaoyang.com")
                        .contact(new Contact("逆流星","http://.com","@qq.com"))
                        .version("3.0")
                        .build())
                //分组名称
                .groupName("test")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ymy.notes.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("我的项目") //  可以用来自定义API的主标题
                .description("我的项目SwaggerAPI管理") // 可以用来描述整体的API
                .termsOfServiceUrl("") // 用于定义服务的域名
                .version("1.0") // 可以用来定义版本。
                .build(); //构建ApiInfo
    }
}
