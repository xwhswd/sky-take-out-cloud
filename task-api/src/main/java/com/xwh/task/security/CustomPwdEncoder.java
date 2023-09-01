package com.xwh.task.security;

import com.alibaba.nacos.common.utils.MD5Utils;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/29
 */
public class CustomPwdEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Utils.encodeHexString(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Utils.encodeHexString(rawPassword.toString().getBytes()));
    }
}
