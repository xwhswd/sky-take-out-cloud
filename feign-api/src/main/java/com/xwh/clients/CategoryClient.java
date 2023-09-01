package com.xwh.clients;

import com.xwh.pojo.Category;
import com.xwh.pojo.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/1
 */
@FeignClient(value = "server-category")
public interface CategoryClient {
    @PostMapping("/category/getById/{id}")
    public Category getById(@PathVariable("id")Long id);
}
