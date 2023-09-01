package com.xwh.clients;

import com.xwh.pojo.Setmeal;
import com.xwh.pojo.vo.SetmealVO;
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
@FeignClient("client-setmeal")
public interface SetmealClient {
    @PostMapping("/setmeal/getById/{id}")
    Setmeal getSetMealById(@PathVariable("id") Long setmealId);

    @PostMapping("/setmeal/count")
    Integer countByMap(@RequestBody Map map);
}
