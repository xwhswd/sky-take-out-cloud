package com.xwh.task.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/29
 */
public class CustomUserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("根据名称认证用户");
        if (username.equals("abc")){
            return new CustomUser(new MyUser(),new ArrayList<GrantedAuthority>());
        }else {
            throw  new RuntimeException("登陆失败");
        }
    }
}
