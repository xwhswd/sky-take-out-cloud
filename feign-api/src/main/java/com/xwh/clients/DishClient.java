package com.xwh.clients;

import com.xwh.pojo.Dish;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/4
 */
@FeignClient("client-dish")
public interface DishClient {
    @PostMapping("/dish/getById/{id}")
    Dish getDishById(@PathVariable("id") Long dishId);

    @PostMapping("/dish/count")
    Integer countByMap(@RequestBody Map map);
}
