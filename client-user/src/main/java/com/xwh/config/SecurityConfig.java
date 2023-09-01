//package com.xwh.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * @author xwh
// * @version 1.0
// * 2023/7/29
// */
////@Configuration
////实现Security提供的WebSecurityConfigurerAdapter类，就可以改变密码校验的规则了
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    //把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验
//    //注意也可以注入PasswordEncoder，效果是一样的，因为PasswordEncoder是BCry..的父类
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    //身份认证
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //由于是前后端分离项目，所以要关闭csrf
//                .csrf().disable()
//                //由于是前后端分离项目，所以session是失效的，我们就不通过Session获取SecurityContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //指定让spring security放行登录接口的规则
//                .authorizeRequests()
//                // 对于登录接口 anonymous表示允许匿名访问
//                .antMatchers("/user/login",
//                        "/favicon.ico",
//                        "/v2/api-docs",
//                        "/swagger-resources/**",
//                        "/webjars/**").anonymous()
//                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated();
//    }
//
//}
