package com.xwh.controller;

import com.xwh.config.JwtProperties;
import com.xwh.constants.JwtClaimsConstant;
import com.xwh.pojo.User;
import com.xwh.pojo.dto.UserLoginDTO;
import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.UserLoginVO;
import com.xwh.service.UserService;
import com.xwh.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@RestController
@RequestMapping("/user")
@Api("用户交互接口")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtProperties jwtProperties;
//
//    @GetMapping("/logout")
//    @ApiOperation("用户登出")
//    public Result logout(UserLoginDTO userLoginDTO){
//        log.info("----用户登出----");
//        userService.login(userLoginDTO);
//        return null;
//    }

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信用户登录：{}",userLoginDTO.getCode());

        //微信登录
        User user = userService.wxLogin(userLoginDTO);

        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.getToken(userLoginDTO.getCode());
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        redisTemplate.opsForValue().set("jwt_"+token,user.getId());
        return Result.success(userLoginVO);
    }

    @PostMapping("/countByMap")
    Integer countByMap(@RequestBody Map map){
        return userService.countByMap(map);
    }

    @PostMapping("/user/getById")
    User getById(Long userId){
        return userService.getById(userId);
    }
}
