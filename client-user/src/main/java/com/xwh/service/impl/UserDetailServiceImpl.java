//package com.xwh.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.xwh.mapper.UserMapper;
//import com.xwh.pojo.User;
//import com.xwh.pojo.dto.LoginDetailDto;
//import com.xwh.pojo.dto.UserLoginDTO;
//import org.springframework.beans.BeanUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
///**
// * @author xwh
// * @version 1.0
// * 2023/7/29
// */
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//    @Resource
//    private UserMapper userMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("openid",username);
//        User user = userMapper.selectOne(queryWrapper);
//        if (user==null){
//            throw new RuntimeException("用户登录异常");
//        }
//        LoginDetailDto loginDetailDto = new LoginDetailDto(user);
//        return loginDetailDto;
//    }
//}
