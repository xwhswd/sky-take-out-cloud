package com.xwh.task.security;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/29
 */
public class CustomFilter extends UsernamePasswordAuthenticationFilter {
    public CustomFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(true);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("..../login"));//规定登录路径的接口和提交方式
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            MyUser user = new ObjectMapper().readValue(request.getInputStream(), MyUser.class);
            //验证用户
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUname(), user.getPwd());
            return this.getAuthenticationManager().authenticate(token);
        }catch (Exception e){
            //返回信息
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //向浏览器发起的响应
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //向浏览器发起的响应
    }
}
