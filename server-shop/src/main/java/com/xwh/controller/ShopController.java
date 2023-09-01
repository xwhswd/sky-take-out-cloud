package com.xwh.controller;

import com.xwh.pojo.result.Result;
import com.xwh.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/shop")
@Api("店铺状态修改接口")
@Slf4j
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
        log.info("----查询状态----");
        int status = shopService.getStatus();
        return Result.success(status);
    }

    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("----设置营业状态:"+status+"----");
        boolean b = shopService.setStatus(status);
        return Result.success();
    }
}
