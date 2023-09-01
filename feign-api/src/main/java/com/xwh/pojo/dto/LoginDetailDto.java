//package com.xwh.pojo.dto;
//
//import com.xwh.pojo.User;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
///**
// * @author xwh
// * @version 1.0
// * 2023/7/29
// */
//@Data //get和set方法
//@NoArgsConstructor //无参构造
//@AllArgsConstructor  //带参构造
//public class LoginDetailDto implements UserDetails {
//    private User user;
//
//    @Override
//    //返回权限消息
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    //获取用户密码
//    public String getPassword() {
//        return "123";
//    }
//
//    @Override
//    //获取用户名
//    public String getUsername() {
//        return user.getName();
//    }
//
//    @Override
//    //账号是否永不过期,true表示是
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    //判断账号是否被锁定。把这个改成true，表示未锁定，不然登录的时候，不让你登录
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    //判断登录凭证是否过期。把这个改成true，表示永不过期
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    //判断用户是否可用。把这个改成true，表示可用状态
//    public boolean isEnabled() {
//        return true;
//    }
//}
