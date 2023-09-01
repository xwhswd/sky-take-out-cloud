package com.xwh.filter;

import com.xwh.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求对象和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //2.判断当前的请求是否为登录，如果是，直接放行
        if(request.getURI().getPath().contains("login")||request.getURI().getPath().contains("status")||
        request.getURI().getPath().contains("doc.html")){
            //放行
            return chain.filter(exchange);
        }
        log.info("查询token");
        //3.获取当前用户的请求头jwt信息
        HttpHeaders headers = request.getHeaders();
        String jwtToken = headers.getFirst("token");
        if (jwtToken==null){
            jwtToken=(String) headers.getFirst("authentication");
        }
        System.out.println(jwtToken);
        try {
            Long o = (Long)redisTemplate.opsForValue().get("jwt_" + jwtToken);
            if (o!=null){
                BaseContext.setCurrentId(o);
            }else {
                throw new Exception("登陆失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            //想客户端返回错误提示信息
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }


        //6.放行
        return chain.filter(exchange);
    }

    /**
     * 优先级设置
     * 值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
