package com.xwh.clients;

import com.xwh.pojo.User;
import com.xwh.pojo.dto.UserLoginDTO;
import com.xwh.pojo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@FeignClient(value = "server-user")
public interface UserClient {
    @GetMapping("/user/login")
    Result login(UserLoginDTO userLoginDTO);

    @PostMapping("/user/countByMap")
    Integer countByMap(@RequestBody Map map);

    @PostMapping("/user/getById")
    User getById(Long userId);
}
