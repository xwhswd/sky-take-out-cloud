package com.xwh.service;

import com.xwh.pojo.User;
import com.xwh.pojo.dto.UserLoginDTO;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
public interface UserService {
    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    User wxLogin(UserLoginDTO userLoginDTO);

    Integer countByMap(Map map);

    User getById(Long userId);
}
