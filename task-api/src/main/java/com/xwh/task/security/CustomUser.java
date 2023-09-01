package com.xwh.task.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/29
 */
public class CustomUser extends User {

    private MyUser myUser;

    public CustomUser(MyUser myUser, Collection<? extends GrantedAuthority> authorities) {
        super(myUser.getUname(),myUser.getPwd(), authorities);
        this.myUser = myUser;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }
}
