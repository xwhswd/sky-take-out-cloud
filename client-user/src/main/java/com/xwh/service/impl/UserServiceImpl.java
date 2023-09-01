package com.xwh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.context.BaseContext;
import com.xwh.mapper.UserMapper;
import com.xwh.pojo.User;
import com.xwh.pojo.dto.UserLoginDTO;
import com.xwh.service.UserService;
import com.xwh.util.HttpClientUtil;
import com.xwh.util.WeChatProperties;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private WeChatProperties weChatProperties;

    @Override
    @Cacheable("user")
    public User login(UserLoginDTO userLoginDTO) {
        String openId = getOpenId(userLoginDTO);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openId);
        User user1 = userMapper.selectOne(wrapper);
        System.out.println(user1.getOpenid());
        return user1;
    }

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        //判断openid是否为空，如果为空表示登录失败，抛出业务异常
        if(openid == null){
            throw new RuntimeException();
        }

        //判断当前用户是否为新用户
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("openid",openid));
        BaseContext.setCurrentId(user.getId());
        //如果是新用户，自动完成注册
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
            User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("openid",openid));
            BaseContext.setCurrentId(user1.getId());
        }
        //返回这个用户对象
        return user;
    }

    @Override
    public Integer countByMap(Map map) {
        return userMapper.selectCount(new QueryWrapper<User>().le("create_time",map.get("end")).
                ge("create_time",map.get("begin")));
    }

    @Override
    public User getById(Long userId) {
        return userMapper.selectById(userId);
    }

    public String getOpenId(UserLoginDTO userLoginDTO){

        return "1212";
    }

    private String getOpenid(String code){
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }


}
