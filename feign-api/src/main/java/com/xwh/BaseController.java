package com.xwh;

import com.xwh.context.BaseContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/7
 */
public class BaseController {
    @Resource
    RedisTemplate redisTemplate;
    @ModelAttribute
    public void setBaseContext(HttpServletRequest request){
        System.out.println("123456789132456789");
        BaseContext.setCurrentId((Long) redisTemplate.opsForValue().get("jwt_"+request.getHeader("token")));
    }
}
